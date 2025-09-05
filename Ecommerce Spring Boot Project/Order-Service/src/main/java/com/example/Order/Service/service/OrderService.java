package com.example.Order.Service.service;


import com.example.Order.Service.dto.request.OrderRequest;
import com.example.Order.Service.dto.response.OrderDTO;
import com.example.Order.Service.entity.Order;
import org.example.dto.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    ApiResponse<Object> placeOrder(OrderRequest request);
    ApiResponse<Object> cancelOrder(String orderId);
    ApiResponse<Object> getOrderHistoryByUser(String userId, Pageable pageable);
    ApiResponse<Object> getOrderDetails(String orderId);
    ApiResponse<Object> getAllOrders(Pageable pageable);
    ApiResponse<Object> getListOrder(Pageable pageable);
    void updateOrderStatus(String orderId, String status);
}
