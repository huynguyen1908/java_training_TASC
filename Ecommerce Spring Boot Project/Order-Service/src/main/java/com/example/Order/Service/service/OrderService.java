package com.example.Order.Service.service;


import com.example.Order.Service.dto.response.OrderDTO;
import com.example.Order.Service.entity.Order;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderDTO orderDTO);
    void cancelOrder(String orderId);
    List<OrderDTO> getOrderHistoryByUser(String userId);
    OrderDTO getOrderDetails(String orderId);
    List<OrderDTO> getAllOrders();
    void updateOrderStatus(String orderId, String status);
}
