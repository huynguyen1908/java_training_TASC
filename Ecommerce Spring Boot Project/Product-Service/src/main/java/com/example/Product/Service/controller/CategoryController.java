package com.example.Product.Service.controller;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateCategoryRequest;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.service.impl.CategoryServiceImpl;
import com.example.Product.Service.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category-list")
    public ResponseEntity<List<Category>> getAllCategories() {
        categoryService.getAllCategories();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) {
        categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-category")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String categoryId, @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-product/{categoryId}")
    public ResponseEntity<Void> addProductToCategory(@PathVariable String categoryId, @RequestBody Product product) {
        categoryService.addProductToCategoryById(categoryId, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove-product")
    public ResponseEntity<Void> removeProductOutOfCategory(@PathVariable String categoryId, @RequestBody Product product) {
        categoryService.removeProductOutOfCategory(categoryId, product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-product")
    public ResponseEntity<List<Product>> getProductList(@RequestParam String categoryId) {
        List<Product> products = categoryService.getProductListCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
