package com.example.Product.Service.config;

import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cartSecurity")
public class CartSecurity {
    @Autowired
    private CartRepository cartRepository;

    public String getUserIdByCartId(String cartId) {
        return cartRepository.findById(cartId)
                .map(Cart::getUserId)
                .orElse(null);
    }
}
