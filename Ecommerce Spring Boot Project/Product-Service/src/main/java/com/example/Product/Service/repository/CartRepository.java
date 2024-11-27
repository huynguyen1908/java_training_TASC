package com.example.Product.Service.repository;

import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.Product;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
