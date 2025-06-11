package com.example.Payment.Service.dto.request;

import com.example.Payment.Service.enums.PaymentGateway;
import com.example.Payment.Service.enums.PaymentMethod;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
    private PaymentGateway gateway;
    private String userId;
    private String returnUrl;
    private String cancelUrl;
}

