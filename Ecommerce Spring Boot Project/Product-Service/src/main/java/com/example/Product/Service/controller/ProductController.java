package com.example.Product.Service.controller;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.service.impl.ProductServiceImpl;
import com.example.Product.Service.service.interfaces.ProductService;
import jakarta.validation.Valid;
import org.example.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ApiResponse<ProductDTO> createProduct(@Valid @ModelAttribute CreateProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/get-details/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
        return ResponseEntity.ok().body(productService.getProductById(productId));
    }

    @DeleteMapping("/delete/{productId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Delete successful");
    }

    @GetMapping("/get-list")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok().body(productService.getProductList(pageable));
    }

    @PutMapping("/update/{productId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.editProduct(productId, request));
    }

    @PostMapping("/upload-image/{productId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadProductImage(@PathVariable String productId, @RequestParam("files") List<MultipartFile> files) {
        try {
            productService.uploadProductImage(files, productId);
            return ResponseEntity.ok("Upload image successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload image failed: " + e.getMessage());
        }
    }

    @GetMapping("/get-image/{productId}")
    public ResponseEntity<Map<Long,String>> getProductImages(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductImageList(productId));
    }

    @PutMapping("/delete-image/{productId}/{imageId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProductImage(@PathVariable String productId, @PathVariable String imageId) {
        productService.deleteProductImage(productId, imageId);
        return ResponseEntity.ok().build();
    }

}
