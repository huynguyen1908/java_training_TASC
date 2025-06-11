package com.example.Payment.Service.service;

import com.example.Payment.Service.dto.request.CreatePaymentRequest;
import com.example.Payment.Service.dto.request.PayPalExecuteRequest;
import com.example.Payment.Service.dto.response.CreatePaymentResponse;
import com.example.Payment.Service.dto.response.PaymentResponse;
import com.example.Payment.Service.dto.response.RefundResponse;
import com.example.Payment.Service.entity.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    CreatePaymentResponse createPayPalPayment(CreatePaymentRequest request);
    String executePayPalPayment(PayPalExecuteRequest request);
    PaymentResponse getPaymentDetail(String id);
    List<PaymentResponse> getPaymentHistoryByUser(String userId);
    PaymentResponse getPaymentByOrder(String orderId);
    RefundResponse refundPayment(String paymentId);
    void handlePaymentCallback(String paymentId, String payerId);
    void cancelPendingPayment(String paymentId);

    Payment findById(String id);

    List<PaymentResponse> getAllPayment();


    String handleVnPayCallback(Map<String, String> allParams);

    String handleMoMoCallback(Map<String, String> params);
}
