package com.example.Product.Service.repository;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateCategoryRequest;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
//    @Query(value = "SELECT * FROM product WHERE category_id = :categoryId", nativeQuery = true)
//    List<Product> getProductListCategory(@Param("categoryId") String categoryId);

}
