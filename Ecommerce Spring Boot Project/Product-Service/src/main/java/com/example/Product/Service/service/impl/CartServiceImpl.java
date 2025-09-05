package com.example.Product.Service.service.impl;

import com.example.Product.Service.dto.response.CartDTO;
import com.example.Product.Service.dto.response.CartItemDTO;
import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.CartItems;
import com.example.Product.Service.entity.Product;

import com.example.Product.Service.mapper.CartMapper;
import com.example.Product.Service.repository.CartItemsRepository;
import com.example.Product.Service.repository.CartRepository;
import com.example.Product.Service.repository.ProductRepository;
import com.example.Product.Service.service.interfaces.CartService;
//import org.example.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartMapper cartMapper;
//
//    private final UserClient userClient;
//
//    @Autowired
//    public CartServiceImpl(UserClient userClient) {
//        this.userClient = userClient;
//    }

    @Override
    @Transactional
    public CartDTO createCart(String userId) {
//        if (!userClient.checkUserExists(userId)) {
//            throw new RuntimeException("User does not exist");
//        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    public void addProductToCart(String cartId, String productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItems item = new CartItems();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);

        cartItemRepository.save(item);
    }

    @Override
    public void removeProductFromCart(String cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public List<CartItemDTO> getCartItems(String cartId) {
        return cartItemRepository.findByCart_CartId(cartId)
                .stream()
                .map(cartMapper::toCartItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartDTO updateCartItemQuantity(String cartItemId, int quantity) {
        CartItems item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return cartMapper.toDTO(item.getCart());
    }
}
