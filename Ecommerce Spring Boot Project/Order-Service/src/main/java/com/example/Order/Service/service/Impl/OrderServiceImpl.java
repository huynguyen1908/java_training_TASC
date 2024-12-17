package com.example.Order.Service.service.Impl;

import com.example.Order.Service.client.PaymentServiceClient;
import com.example.Order.Service.dto.request.OrderDTO;
import com.example.Order.Service.dto.request.PaymentRequest;
import com.example.Order.Service.entity.Order;
import com.example.Order.Service.mapper.OrderMapper;
import com.example.Order.Service.repository.OrderRepository;
import com.example.Order.Service.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    PaymentServiceClient paymentServiceClient;

    @Override
    @Transactional
    public Order createdOrder(OrderDTO orderDTO){
        orderDTO.setStatus("PENDING");
        orderDTO.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(orderMapper.toOrder(orderDTO));

        return orderRepository.save(savedOrder);
    }
//    void deleteOrder();
//    void updateOrder();
//    List<Order> getOrderList();
//    Order getOrderById();
}
