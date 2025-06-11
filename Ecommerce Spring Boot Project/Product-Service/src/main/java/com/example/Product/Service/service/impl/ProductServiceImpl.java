package com.example.Product.Service.service.impl;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
//import com.example.Product.Service.entity.Category;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.enums.NotificationType;
import com.example.Product.Service.event.NotificationEvent;
import com.example.Product.Service.event.ProductCreatedEvent;
import com.example.Product.Service.mapper.ProductMapper;
//import com.example.Product.Service.repository.CategoryRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<ProductDTO> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDTO(product);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDTO editProduct (String productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProductFromRequest(request, product);
        productRepository.save(product);

        return productMapper.toDTO(product);
    }

}
