package com.example.Order.Service.service;

import com.example.Order.Service.entity.Order;

import java.util.List;

public interface OrderService {
    Order createdOrder(Order order);
    void deleteOrder();
    void updateOrder();
    List<Order> getOrderList();
    Order getOrderById();
}
