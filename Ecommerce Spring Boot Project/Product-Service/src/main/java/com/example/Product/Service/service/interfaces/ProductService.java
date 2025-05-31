package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    void createProduct(CreateProductRequest productRequest);

    List<ProductDTO> getProductList();

    ProductDTO getProductById(String productId);

    void deleteProduct(String productId);

    ProductDTO editProduct(String productId, UpdateProductRequest request);

//    void uploadProductImage(String url);
//    List<ProductDTO> filterProductBy(String name, Decimal price, String brand, String category);

}
