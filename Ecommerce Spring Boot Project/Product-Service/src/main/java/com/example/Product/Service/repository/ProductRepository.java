package com.example.Product.Service.repository;

import com.example.Product.Service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
//    @Query(value = "SELECT * FROM product WHERE category_id = :categoryId", nativeQuery = true)
//    List<Product> getProductListCategory(@Param("categoryId") String categoryId);
//
//    @Query(value = "SELECT * FROM product WHERE cart_id = :cartId", nativeQuery = true)
//    List<Product> getProductListCart(@Param("cartId") String cartId);

    List<Product> findByCategory_CategoryId(String categoryId);
    boolean existsBySkuCode(String skuCode);
}
