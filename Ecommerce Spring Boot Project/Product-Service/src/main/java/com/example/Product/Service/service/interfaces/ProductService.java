package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    void createProduct(CreateProductRequest productRequest);

    Page<ProductDTO> getProductList(Pageable pageable);

    ProductDTO getProductById(String productId);

    void deleteProduct(String productId);

    ProductDTO editProduct(String productId, UpdateProductRequest request);

    void uploadProductImage(List<MultipartFile> files, String productId);

    List<String> getProductImageList(String productId);

    void deleteProductImage(String productId, String imageId);
}
