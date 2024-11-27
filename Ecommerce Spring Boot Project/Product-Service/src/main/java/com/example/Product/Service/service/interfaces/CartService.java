package com.example.Product.Service.service.interfaces;

import com.example.Product.Service.dto.request.AddToCartRequest;
import com.example.Product.Service.dto.request.CreateCartRequest;
import com.example.Product.Service.dto.request.RemoveFromCartRequest;
import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.Product;

import java.util.List;

public interface CartService {

    Cart createCart(String userId);

    void addProductToCart(String cartId, Product product);

    void removeProductFromCart(String cartId, Product product);

    List<Product> getProductListCart(String cartId);

    void clearCart(String cartId);
}
