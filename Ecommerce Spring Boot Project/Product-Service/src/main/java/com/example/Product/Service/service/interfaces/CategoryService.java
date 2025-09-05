package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.CreateCategoryRequest;

import com.example.Product.Service.dto.response.CategoryDTO;
import com.example.Product.Service.dto.response.ProductDTO;
import org.example.dto.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface CategoryService {
    ApiResponse<Object> getAllCategories(Pageable pageable);

    ApiResponse<Object> getProductsByCategory(String categoryId, Pageable pageable);

    ApiResponse<Object> createCategory(CreateCategoryRequest request);

    ApiResponse<Object> getCategoryById(String categoryId);

    ApiResponse<Object> editCategory(String categoryId, CreateCategoryRequest request);


    ApiResponse<Object> addProductToCategory(String categoryId, String productId);

    ApiResponse<Object> getCategoryIdAndCategoryName();

    ApiResponse<Object> getCategoryNameById(String categoryId);


}
