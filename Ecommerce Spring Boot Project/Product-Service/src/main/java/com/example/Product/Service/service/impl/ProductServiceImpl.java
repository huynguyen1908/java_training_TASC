package com.example.Product.Service.service.impl;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
//import com.example.Product.Service.entity.Category;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.mapper.ProductMapper;
//import com.example.Product.Service.repository.CategoryRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void createProduct(CreateProductRequest productRequest) {
        Product product = productMapper.fromCreateRequest(productRequest);
        productRepository.save(product);
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
