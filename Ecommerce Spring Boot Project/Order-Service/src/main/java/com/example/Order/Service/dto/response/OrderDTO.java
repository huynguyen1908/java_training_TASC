package com.example.Order.Service.dto.response;


import com.example.Order.Service.dto.response.OrderDetailDTO;
import com.example.Order.Service.entity.OrderDetail;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    String orderId;
    String userId;
    String status;
    LocalDateTime orderDate;
    String shippingMethod;
    String paymentMethod;
    LocalDateTime shippedAt;
    String address;
    LocalDateTime createdAt;
    List<OrderDetailDTO> orderDetailList;
}
