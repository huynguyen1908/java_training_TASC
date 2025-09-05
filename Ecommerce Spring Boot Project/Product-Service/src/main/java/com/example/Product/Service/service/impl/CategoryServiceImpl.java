package com.example.Product.Service.service.impl;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.response.CategoryDTO;
import com.example.Product.Service.dto.response.ProductDTO;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.mapper.CategoryMapper;
import com.example.Product.Service.mapper.ProductMapper;
import com.example.Product.Service.repository.CategoryRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.CategoryService;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.PageResponse;
import org.example.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ApiResponse<Object> getAllCategories(Pageable pageable) {
        Page<CategoryDTO> categories = categoryRepository.findAll(pageable)
                .map(categoryMapper::toDTO);
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(new PageResponse<>(categories.getContent(), categories.getTotalPages(), categories.getTotalElements()))
                .build();
    }

    @Override
    public ApiResponse<Object> getProductsByCategory(String categoryId, Pageable pageable) {
        Page<ProductDTO> products = productRepository.findByCategory_CategoryId(categoryId, pageable)
                .map(productMapper::toDTO);

        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(products)
                .build();
    }

    @Override
    public ApiResponse<Object> createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(request.getCreatedBy());
        categoryRepository.save(category);
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(categoryMapper.toDTO(category))
                .build();
    }

    @Override
    public ApiResponse<Object> addProductToCategory(String categoryId, String productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setCategory(category);
        productRepository.save(product);
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(categoryMapper.toDTO(category))
                .build();
    }

    @Override
    public ApiResponse<Object> getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(categoryMapper.toDTO(category))
                .build();
    }

    @Override
    public ApiResponse<Object> editCategory(String categoryId, CreateCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedAt(LocalDateTime.now());
        category.setUpdatedBy(request.getCreatedBy());

        categoryRepository.save(category);
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(categoryMapper.toDTO(category))
                .build();
    }


    @Override
    public ApiResponse<Object> getCategoryIdAndCategoryName() {
        Map<String, String> categoryName = categoryRepository.getCategoryIdAndCategoryName().stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (String) obj[1]
                ));
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(categoryName)
                .build();
    }

    @Override
    public ApiResponse<Object> getCategoryNameById(String categoryId) {
        return ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .message(StatusCode.SUCCESS.getMessage())
                .data(categoryRepository.findCategoryNameById(categoryId))
                .build();
    }
}
