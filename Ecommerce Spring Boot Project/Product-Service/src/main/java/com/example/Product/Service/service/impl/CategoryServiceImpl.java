package com.example.Product.Service.service.impl;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateCategoryRequest;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.repository.CategoryRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCreatedBy(request.getCreatedBy());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(String categoryId, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedBy(request.getUpdatedBy());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public void addProductToCategoryById(String categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException());
        if (productRepository.findById(product.getId()).isPresent()) {
            category.addProduct(product);
            categoryRepository.save(category);
        }
    }

    @Override
    public void removeProductOutOfCategory(String categoryId,Product product){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException());
        for (Product p : category.getProducts()){
            if(product.getId().equals(p.getId())) {
                category.removeProduct(p);
                product.setCategory(null);
                productRepository.save(product);
                categoryRepository.save(category);
            }
        }
    }

    @Override
    public List<Product> getProductListCategory(String categoryId){
        return productRepository.getProductListCategory(categoryId);
    }
}
