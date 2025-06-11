package com.example.Payment.Service.dto.request;

import lombok.Data;

@Data
public class PayPalExecuteRequest {
    private String paymentId;
    private String payerId;
}
