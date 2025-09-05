package com.example.Product.Service.repository;

import com.example.Product.Service.dto.request.CreateCategoryRequest;
import com.example.Product.Service.dto.request.UpdateCategoryRequest;
import com.example.Product.Service.entity.Category;
import com.example.Product.Service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
//    @Query(value = "SELECT * FROM product WHERE category_id = :categoryId", nativeQuery = true)
//    List<Product> getProductListCategory(@Param("categoryId") String categoryId);


    @Override
    Page<Category> findAll(Pageable pageable);

    @Query(value = "select c.name " +
            "from category c left join product p on p.category_id = c.category_id " +
            "where p.product_id = :productId", nativeQuery = true)
    String findProductCategoryByProductId(@Param("productId") String productId);

//    @Query(value = "select c.id ,c.name " +
//            "from category c " +
//            "where c.category_id = :categoryId", nativeQuery = true)
//    Optional<String> findCategoryNameById(String categoryId);

    @Query(value = "select c.category_id, c.name " +
            "from category c", nativeQuery = true)
    List<Object[]> getCategoryIdAndCategoryName();

    @Query(value = "select c.name from category c where c.category_id = :categoryId", nativeQuery = true)
    String findCategoryNameById(@Param("categoryId") String categoryId);
}
