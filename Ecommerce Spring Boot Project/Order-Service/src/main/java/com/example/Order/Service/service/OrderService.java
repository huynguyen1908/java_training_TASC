package com.example.Order.Service.service;

import com.example.Order.Service.dto.request.OrderDTO;
import com.example.Order.Service.entity.Order;

import java.util.List;

public interface OrderService {
    Order createdOrder(OrderDTO orderDTO);
//    void deleteOrder();
//    void updateOrder();
//    List<Order> getOrderList();
//    Order getOrderById();
}
