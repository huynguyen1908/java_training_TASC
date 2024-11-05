package com.example.Product.Service.service;

import com.example.Product.Service.dto.ProductRequest;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
    }
}
