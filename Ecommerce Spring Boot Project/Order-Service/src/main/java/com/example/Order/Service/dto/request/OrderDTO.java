package com.example.Order.Service.dto.request;


import com.example.Order.Service.dto.request.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    String orderId;
    String userId;
    String status;
    LocalDateTime orderDate;
    int discount;
    LocalDateTime shippedDate;
    LocalDateTime finishedDate;
    String shipCode;
    String transportCompany;
    List<OrderDetailDTO> orderDetail;
}
