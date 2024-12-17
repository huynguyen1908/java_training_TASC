package com.example.Payment.Service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    private String paymentMethod;
    private String status;
    private LocalDateTime date;
    private String transactionId;

    @Column(name = "order_id")
    private String orderId;
}
