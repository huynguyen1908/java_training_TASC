package com.example.Order.Service.service.Impl;

import com.example.Order.Service.client.PaymentServiceClient;
import com.example.Order.Service.dto.request.PaymentRequest;
import com.example.Order.Service.entity.Order;
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

    @Override
    @Transactional
    public Order createdOrder(Order order){
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Tạo yêu cầu thanh toán và gọi đến Payment Service qua Feign Client
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(savedOrder.getOrderId());
        paymentRequest.setAmount(order.getDiscount());
        paymentRequest.setPaymentMethod("Credit Card");

        String paymentStatus = PaymentServiceClient.createPayment(paymentRequest);

        if ("SUCCESS".equals(paymentStatus)) {
            savedOrder.setStatus("CONFIRMED");
        } else {
            savedOrder.setStatus("CANCELLED");
        }

        return orderRepository.save(savedOrder);
    }
//    void deleteOrder();
//    void updateOrder();
//    List<Order> getOrderList();
//    Order getOrderById();
}
