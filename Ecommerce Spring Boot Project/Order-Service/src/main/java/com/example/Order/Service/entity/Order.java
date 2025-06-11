package com.example.Order.Service.entity;

import com.example.Order.Service.enums.PaymentMethod;
import com.example.Order.Service.enums.ShippingMethod;
import com.example.Order.Service.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String orderId;

    String userId;

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    LocalDateTime shippedAt;

    String address;

    LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<OrderDetail> orderDetailList;
}
