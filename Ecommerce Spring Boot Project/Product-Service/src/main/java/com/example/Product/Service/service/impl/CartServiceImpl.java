package com.example.Product.Service.service.impl;

import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.Product;

import com.example.Product.Service.repository.CartRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart createCart(String userId) {
        Cart cart = new Cart();
        cart.setUserId( userId);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void addProductToCart(String cartId, Product product) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        product = productRepository.findById(product.getId())
                .orElseThrow(()->new RuntimeException("Product not found"));
        for(Product p : cart.getProducts()){
            if(!p.getId().equals(product.getId())){
                cart.addProduct(product);
                productRepository.save(product);
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(String cartId, Product product) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException());
        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("Product not found");
        } else {
            for(Product p : cart.getProducts()){
                if(!p.getId().equals(product.getId())){
                    cart.removeProduct(p);
                    product.setCart(null);
                    productRepository.save(product);
                    cartRepository.save(cart);
                }
            }
        }
    }

    @Override
    public List<Product> getProductListCart(String cartId) {
        return productRepository.getProductListCart(cartId);
    }

    @Override
    public void clearCart(String cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException());
        List<Product> productList = new ArrayList<>();
        // productList.add(cart.getProductId().);
        if(!productList.isEmpty()){
            productList.clear();
        }
    }
}
