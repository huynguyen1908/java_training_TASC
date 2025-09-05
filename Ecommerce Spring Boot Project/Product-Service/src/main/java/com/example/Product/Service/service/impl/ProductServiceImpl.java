package com.example.Product.Service.service.impl;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Image;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.enums.NotificationType;
import com.example.Product.Service.event.NotificationEvent;
import com.example.Product.Service.event.ProductCreatedEvent;
import com.example.Product.Service.mapper.ProductMapper;
import com.example.Product.Service.repository.CategoryRepository;
import com.example.Product.Service.repository.ImageRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.CategoryService;
import com.example.Product.Service.service.interfaces.ProductService;

import jakarta.transaction.Transactional;
import org.example.client.CloudinaryClient;
//import org.example.client.UserClient;
import org.example.client.InventoryClient;
import org.example.dto.response.ApiResponse;
import org.example.exception.StatusCode;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, NotificationEvent> notificationKafkaTemplate;

    private final CloudinaryClient cloudinaryClient;
    private final InventoryClient inventoryClient;

    @Autowired
    private ImageRepository imageRepository;

    private static JwtUtil jwtUtil;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(CloudinaryClient cloudinaryClient,
                              InventoryClient inventoryClient) {
        this.cloudinaryClient = cloudinaryClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    @Transactional
    public ApiResponse<ProductDTO> createProduct(CreateProductRequest productRequest) {
        Product product = productMapper.fromCreateRequest(productRequest);
        product.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));

        if (productRequest.getSkuCode() == null || productRequest.getSkuCode().isBlank()) {
            String generatedSku = "SKU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            product.setSkuCode(generatedSku);
        } else {
            product.setSkuCode(productRequest.getSkuCode());
        }

        if (productRepository.existsBySkuCode(product.getSkuCode())) {
            throw new IllegalArgumentException("SKU code already exists.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        product.setCreatedBy(username);
        product.setCreatedAt(LocalDateTime.now());
        product.setIsActive(1L);
        productRepository.save(product);

        uploadProductImage(productRequest.getFiles(), product.getProductId());
        categoryService.addProductToCategory(productRequest.getCategoryId(), product.getProductId());

        ProductCreatedEvent event = new ProductCreatedEvent(
                product.getSkuCode(),
                product.getName(),
                product.getPrice(),
                productRequest.getQuantity()
        );
        kafkaTemplate.send("product-created-topic", event);

        NotificationEvent notificationEvent = new NotificationEvent(
                null,
                "Sản phẩm mới: " + product.getName(),
                NotificationType.NEW_PRODUCT,
                product.getProductId()
        );
        notificationKafkaTemplate.send("new-product-topic", notificationEvent);

        return ApiResponse.<ProductDTO>builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(productMapper.toDTO(product))
                .build();
    }

    @Override
    public Page<ProductDTO> getProductList(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOPage = productPage.map(productMapper::toDTO);
        return productDTOPage;
    }

    @Override
    public ProductDTO getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDTO productDTO = productMapper.toDTO(product);
        Map<Long,String> imageUrls = getProductImageList(productId);
        productDTO.setImageUrl(imageUrls);
        productDTO.setCategoryMap(productRepository.getCategoryIdAndCategoryNameByProductId(productId).stream()
                .filter(obj -> obj[0] != null && obj[1] != null)
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (String) obj[1]
                )));
        productDTO.setQuantity(inventoryClient.getStockByProduct(product.getSkuCode()));
        return productDTO;
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDTO editProduct(String productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        product.setUpdatedBy(username);
        product.setUpdatedAt(LocalDateTime.now());

        productMapper.updateProductFromRequest(request, product);
        productRepository.save(product);
        inventoryClient.addStock(product.getSkuCode(), product.getName(), request.getQuantity(), request.getPrice());
        return productMapper.toDTO(product);
    }

    @Override
    public void uploadProductImage(List<MultipartFile> files, String productId) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        System.out.println("total files: " + files.size());
        String username = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();

        List<Future<?>> futures = new ArrayList<>();
        for (MultipartFile file : files) {
            futures.add(service.submit(() -> {
                try {
                    String fileName = file.getOriginalFilename();
                    // use client module
                    String filePath = cloudinaryClient.uploadMultipartFile(file, fileName);

                    //save image information to database
                    Image image = new Image();
                    image.setImageName(fileName);
                    image.setImagePath(filePath);

                    //username
                    image.setCreatedBy(username);
                    image.setCreatedAt(LocalDateTime.now());
                    image.setObjectId(productId);
                    image.setIsActive(1L);
                    image.setSource("product-service");

                    imageRepository.save(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
                }
            }));
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException("Error occurred while uploading images", e);
            }
        }
        service.shutdown();
    }

    @Override
    public Map<Long,String> getProductImageList(String productId) {
        Map<Long,String> images = imageRepository.findImagePathByProductId(productId).stream()
                .collect(Collectors.toMap(
                        row-> (Long) row[0],
                        row-> (String) row[1]
                ));
        return images;
    }

    @Override
    public void deleteProductImage(String productId, String imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        if (!image.getObjectId().equals(productId)) {
            throw new RuntimeException("Image does not belong to this product");
        }
        image.setIsActive(0L);
        String username = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        image.setUpdatedBy(username);
        image.setUpdatedAt(LocalDateTime.now());
        imageRepository.save(image);
    }
}
