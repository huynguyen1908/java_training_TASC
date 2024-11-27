package com.example.Order.Service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Table(name = "order_detail")
public class OrderDetail {
    @JoinColumn(name = "order_id", nullable = false)
    String order_id;

    String product_id;
    int quantity;
    double total_price;
    double discount_price;
}
