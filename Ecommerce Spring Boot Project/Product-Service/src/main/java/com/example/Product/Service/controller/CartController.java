package com.example.Product.Service.controller;

//import com.example.Product.Service.dto.request.AddToCartRequest;
import com.example.Product.Service.dto.request.CreateCartRequest;
import com.example.Product.Service.dto.request.RemoveFromCartRequest;
import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.Product;
import com.example.Product.Service.service.impl.CartServiceImpl;
import com.example.Product.Service.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@PathVariable @Validated String userId){
        cartService.createCart(userId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/add-product")
    public ResponseEntity<Void> addProductToCart(@PathVariable String cartId, @RequestBody Product product) {
        cartService.addProductToCart(cartId, product);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Cart> getCartById(@PathVariable String id) {
//        return cartService.getUserCart(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/remove-product")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable String cartId, @RequestBody Product product){
        cartService.removeProductFromCart(cartId, product);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/get-cart")
    public List<Product> getProductUserCart(@RequestParam String cartId){
        List productList = cartService.getProductListCart(cartId);
        return (List<Product>) ResponseEntity.ok(productList);
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<Void> clearCart(@PathVariable String cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }
}

