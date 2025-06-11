package com.example.Payment.Service.dto.response;

import com.example.Payment.Service.enums.PaymentMethod;
import com.example.Payment.Service.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private String paymentId;
    private String orderId;
    private String providerTransactionId;
    private String redirectUrl;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
}
