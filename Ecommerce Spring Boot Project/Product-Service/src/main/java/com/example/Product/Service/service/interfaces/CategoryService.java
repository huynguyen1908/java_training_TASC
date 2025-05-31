package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.dto.response.CategoryDTO;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getProductsByCategory(String categoryId);

    CategoryDTO createCategory(CreateCategoryRequest request);

    CategoryDTO getCategoryById(String categoryId);

    CategoryDTO editCategory(String categoryId, CreateCategoryRequest request);

//    void deleteCategory(String categoryId);

    CategoryDTO addProductToCategory(String categoryId, String productId);

//    void removeProductOutOfCategory(String categoryId, Product product);

}
