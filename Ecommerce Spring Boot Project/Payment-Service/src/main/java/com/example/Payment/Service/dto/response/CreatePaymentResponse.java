package com.example.Payment.Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePaymentResponse {
    private String paymentId;
    private String approvalUrl;
    private String qrCodeBase64;
}
