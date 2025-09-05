package com.example.Product.Service.repository;

import com.example.Product.Service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByCategory_CategoryId(String categoryId, Pageable pageable);
    boolean existsBySkuCode(String skuCode);

    List<String> findImagePathByProductId(String productId);

    @Query(value = "select p from Product p where p.isActive = 1 order by p.createdAt desc")
    Page<Product> findAll(Pageable pageable);

    @Query(value = "select p.category_id, c.name from product p left join category c on p.category_id = c.category_id where product_id = :productId", nativeQuery = true)
    List<Object[]> getCategoryIdAndCategoryNameByProductId(@Param("productId") String productId);
}
