package com.example.Order.Service.controller;

import com.example.Order.Service.dto.response.OrderDTO;
import com.example.Order.Service.entity.Order;
import com.example.Order.Service.service.OrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Data
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        orderService.placeOrder(orderDTO);
        return ResponseEntity.ok("Place order successful");
    }

    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("@orderSecurity.isOrderOwner(#orderId)")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Cancel order");
    }

    @GetMapping("/user/{userId}/history")
    @PreAuthorize("#userId == authentication.principal or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrderHistoryByUser(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrderHistoryByUser(userId));
    }


    @GetMapping("/{id}")
    @PreAuthorize("@orderSecurity.hasAccessToOrder(#orderId)")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}
