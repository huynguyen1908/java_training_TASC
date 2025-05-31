package com.example.Product.Service.controller;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.response.CategoryDTO;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(categoryService.getProductsByCategory(categoryId));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @PostMapping("/{categoryId}/add-product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addProductToCategory(
            @PathVariable String categoryId,
            @PathVariable String productId
    ) {
        return ResponseEntity.ok(categoryService.addProductToCategory(categoryId, productId));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PutMapping("/edit/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable String categoryId,
                                                    @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.editCategory(categoryId, request));
    }
}
