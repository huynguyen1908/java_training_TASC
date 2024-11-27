package com.example.Order.Service.service.Impl;

import com.example.Order.Service.entity.OrderDetail;
import com.example.Order.Service.repository.OrderDetailRepository;
import com.example.Order.Service.service.OrderDetailService;
import com.example.Order.Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderService orderService;

    public Optional<OrderDetail> getOrderDetailByOrderId(String orderId){
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(orderId);
        return orderDetail;
    }
}
