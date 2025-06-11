package com.example.Order.Service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    Order order;

    String skuCode;
    int quantity;
    double totalPrice;
}
