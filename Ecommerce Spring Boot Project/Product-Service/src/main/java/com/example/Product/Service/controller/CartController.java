package com.example.Product.Service.controller;

import com.example.Product.Service.dto.response.CartDTO;
import com.example.Product.Service.dto.response.CartItemDTO;
import com.example.Product.Service.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<CartDTO> createCart(@RequestParam String userId) {
        CartDTO cartDTO = cartService.createCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
    }

    @PreAuthorize("authentication.name == @cartSecurity.getUserIdByCartId(#cartId)")
    @PostMapping("/{cartId}/add")
    public ResponseEntity<?> addProductToCart(
            @PathVariable String cartId,
            @RequestParam String productId,
            @RequestParam(defaultValue = "1") int quantity) {
        cartService.addProductToCart(cartId, productId, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    @PreAuthorize("authentication.name == @cartSecurity.getUserIdByCartId(#cartId)")
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable String cartItemId) {
        cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.ok("Cart item removed");
    }

    @PreAuthorize("authentication.name == @cartSecurity.getUserIdByCartId(#cartId)")
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable String cartId) {
        return ResponseEntity.ok(cartService.getCartItems(cartId));
    }

    @PreAuthorize("authentication.name == @cartSecurity.getUserIdByCartId(#cartId)")
    @PutMapping("/item/{cartItemId}/update")
    public ResponseEntity<CartDTO> updateCartItemQuantity(
            @PathVariable String cartItemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(cartItemId, quantity));
    }
}

