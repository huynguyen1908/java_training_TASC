package com.example.Product.Service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedEvent {
    private String skuCode;
    private String name;
    private double importPrice;
    private int quantity;
}
