package com.example.Payment.Service.entity;

import com.example.Payment.Service.enums.PaymentGateway;
import com.example.Payment.Service.enums.PaymentMethod;
import com.example.Payment.Service.enums.PaymentStatus;
import com.example.Payment.Service.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private BigDecimal amount;
    private String currency;

    private String providerTransactionId;

    private String redirectUrl;
    private String responseCode;
    private String responseMessage;

    private boolean isSuccess;
    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String orderId;

    @Column(columnDefinition = "TEXT")
    private String extraData;

    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    private LocalDateTime refundedAt;

    private String refundTransactionId;

    private String userId;
}
