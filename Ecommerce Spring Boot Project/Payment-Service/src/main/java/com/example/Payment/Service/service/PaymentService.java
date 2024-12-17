package com.example.Payment.Service.service;

import com.example.Payment.Service.dto.request.CreatePaymentRequest;
import com.example.Payment.Service.entity.Payment;

public interface PaymentService {
    Payment createPayment(CreatePaymentRequest createPaymentRequest);
}
