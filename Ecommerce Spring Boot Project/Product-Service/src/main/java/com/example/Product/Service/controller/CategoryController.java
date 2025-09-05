package com.example.Product.Service.controller;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.response.CategoryDTO;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.service.interfaces.CategoryService;
import org.example.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public ApiResponse<Object> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{categoryId}/products")
    public ApiResponse<Object> getProductsByCategory(@PathVariable String categoryId, Pageable pageable) {
        return categoryService.getProductsByCategory(categoryId, pageable);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Object> createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PostMapping("/{categoryId}/add-product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Object> addProductToCategory(
            @PathVariable String categoryId,
            @PathVariable String productId
    ) {
        return categoryService.addProductToCategory(categoryId, productId);
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<Object> getCategoryById(@PathVariable String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/edit/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Object> editCategory(@PathVariable String categoryId,
                                                    @RequestBody CreateCategoryRequest request) {
        return categoryService.editCategory(categoryId, request);
    }

    @GetMapping("/name")
    public ApiResponse<Object> getCategoryIdAndCategoryName() {
        return categoryService.getCategoryIdAndCategoryName();
    }

    @GetMapping("/category-name/{categoryId}")
    public ApiResponse<Object> getCategoryNameById(String categoryId) {
        return categoryService.getCategoryNameById(categoryId);
    }
}
