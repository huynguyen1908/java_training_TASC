package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.response.CartDTO;
import com.example.Product.Service.dto.response.CartItemDTO;
import java.util.List;

public interface CartService {

    CartDTO createCart(String userId);

    void addProductToCart(String cartId, String productId, int quantity);

    void removeProductFromCart(String cartItemId);

    List<CartItemDTO> getCartItems(String cartId);

    CartDTO updateCartItemQuantity(String cartItemId, int quantity);
}
