package com.example.Order.Service.dto.request;


import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private double amount;
    private String paymentMethod;
}
