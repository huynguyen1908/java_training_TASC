package com.example.Order.Service.controller;

import com.example.Order.Service.dto.request.OrderRequest;
import com.example.Order.Service.dto.response.OrderDTO;
import com.example.Order.Service.entity.Order;
import com.example.Order.Service.service.OrderService;
import lombok.Data;
import org.example.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/place-order")
    public ApiResponse<Object> placeOrder(@RequestBody OrderRequest request){
        return orderService.placeOrder(request);
    }

    @PutMapping("/{orderId}/cancel")
//    @PreAuthorize("@orderSecurity.isOrderOwner(#orderId)")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Cancel order");
    }

    @GetMapping("/user/{userId}/history")
//    @PreAuthorize("#userId == authentication.principal or hasRole('ADMIN')")
    public ApiResponse<Object> getOrderHistoryByUser(@PathVariable String userId, Pageable pageable) {
        return orderService.getOrderHistoryByUser(userId, pageable);
    }


    @GetMapping("/order-detail/{orderId}")
//    @PreAuthorize("@orderSecurity.hasAccessToOrder(#orderId)")
    public ApiResponse<Object> getOrderDetails(@PathVariable String orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @GetMapping("/get-order-list")
//    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Object> getListOrder(Pageable pageable) {
        return orderService.getListOrder(pageable);
    }

    @PutMapping("{orderId}/status")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}
