package com.example.Product.Service.repository;

import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, String> {

    List<CartItems> findByCart_CartId(String cartId);
}
