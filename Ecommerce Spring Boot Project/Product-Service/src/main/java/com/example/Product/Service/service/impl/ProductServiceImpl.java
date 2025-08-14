package com.example.Product.Service.service.impl;

import com.example.Product.Service.client.CloudinaryClient;
import com.example.Product.Service.client.UserClient;
import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Image;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.enums.NotificationType;
import com.example.Product.Service.event.NotificationEvent;
import com.example.Product.Service.event.ProductCreatedEvent;
import com.example.Product.Service.mapper.ProductMapper;
import com.example.Product.Service.repository.ImageRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.ProductService;
import com.example.Product.Service.util.JwtUtil;

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

    @Autowired
    private CloudinaryClient cloudinaryClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ImageRepository imageRepository;

    private static JwtUtil jwtUtil;

    @Override
    public void createProduct(CreateProductRequest productRequest) {
        Product product = productMapper.fromCreateRequest(productRequest);
        product.setCreatedAt(LocalDateTime.now());

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
        productRepository.save(product);

        ProductCreatedEvent event = new ProductCreatedEvent(
                product.getSkuCode(),
                product.getName(),
                product.getPrice()
        );
        kafkaTemplate.send("product-created-topic", event);

        NotificationEvent notificationEvent = new NotificationEvent(
                null,
                "Sản phẩm mới: " + product.getName(),
                NotificationType.NEW_PRODUCT,
                product.getProductId()
        );
        notificationKafkaTemplate.send("new-product-topic", notificationEvent);

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
        List<String> imageUrls = imageRepository.findImagePathByProductId(productId);
        productDTO.setImageUrl(imageUrls);
        productDTO.setCategory(productRepository.findProductCategoryByProductId(productId));
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
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            uploadProductImage(request.getImages(), productId);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        request.setUpdatedBy(username);
        request.setUpdatedDate(LocalDateTime.now());

        productMapper.updateProductFromRequest(request, product);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public void uploadProductImage(List<MultipartFile> files, String productId) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        System.out.println("total files: " + files.size());

        List<Future<?>> futures = new ArrayList<>();
        for (MultipartFile file : files) {
            futures.add(service.submit(() -> {
                try {
                    String fileName = file.getOriginalFilename();
                    String filePath = cloudinaryClient.uploadMultipartFile(file, fileName);


                    //save image information to database
                    Image image = new Image();
                    image.setImageName(fileName);
                    image.setImagePath(filePath);

                    //username
                    String username = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
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
    public List<String> getProductImageList(String productId) {
        List<String> images = imageRepository.findImagePathByProductId(productId);
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
