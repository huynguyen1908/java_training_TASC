package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(String categoryId);

    Category createCategory(CreateCategoryRequest request);


    Category updateCategory(String categoryId, UpdateCategoryRequest request);

    void deleteCategory(String categoryId);
    //void addProductToCategoryByName(String categoryId,String productName);

    void addProductToCategoryById(String categoryId, Product product);

    void removeProductOutOfCategory(String categoryId, Product product);

    List<Product> getProductListCategory(String categoryId);
}
