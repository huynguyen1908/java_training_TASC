package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    void createProduct(CreateProductRequest productRequest);

    List<Product> getAllProducts();

    Optional<Product> getProductById(String productId);

    void deleteProduct(String id);

    Product updateProduct(String id, UpdateProductRequest request);
    //void addProductToCategory(Product product, String categoryId);
}
