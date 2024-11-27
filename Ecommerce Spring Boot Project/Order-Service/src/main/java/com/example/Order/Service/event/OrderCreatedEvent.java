package com.example.Order.Service.event;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private double amount;
    private String userId;
}
