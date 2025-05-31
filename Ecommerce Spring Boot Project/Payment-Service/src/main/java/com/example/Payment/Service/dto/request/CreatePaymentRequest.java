package com.example.Payment.Service.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentRequest {
    private String paymentMethod;
    private String orderId;
}
