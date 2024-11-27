package com.example.Order.Service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String orderId;

    @Column(name = "user_id")
    String userId;

    String status;
    @Column(name = "order_date")
    LocalDateTime orderDate;

    int discount;

    @Column(name = "shipped_date")
    LocalDateTime shippedDate;

    @Column(name = "finished_date")
    LocalDateTime finishedDate;

    @Column(name = "ship_code")
    String shipCode;

    @Column(name = "transport_company")
    String transportCompany;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderDetail> orderDetailList;
}
