package com.example.Payment.Service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RefundResponse {
    private String paymentId;
    private boolean success;
    private String message;
    private LocalDateTime refundedAt;
}
