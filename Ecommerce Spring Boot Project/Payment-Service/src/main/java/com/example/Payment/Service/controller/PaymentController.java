package com.example.Payment.Service.controller;

import com.example.Payment.Service.client.PayPalClient;
import com.example.Payment.Service.dto.request.CreatePaymentRequest;
import com.example.Payment.Service.dto.request.PayPalExecuteRequest;
import com.example.Payment.Service.dto.response.CreatePaymentResponse;
import com.example.Payment.Service.dto.response.PaymentResponse;
import com.example.Payment.Service.dto.response.RefundResponse;
import com.example.Payment.Service.entity.Payment;
import com.example.Payment.Service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/paypal/create")
    public ResponseEntity<CreatePaymentResponse> createPayPalPayment(@RequestBody CreatePaymentRequest request) {
        CreatePaymentResponse response = paymentService.createPayPalPayment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/paypal/execute")
    public ResponseEntity<String> executePayPalPayment(@RequestBody PayPalExecuteRequest request) {
        String result = paymentService.executePayPalPayment(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<RefundResponse> refundPayment(@PathVariable String paymentId) {
        RefundResponse response = paymentService.refundPayment(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentDetail(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentDetail(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentHistoryByUser(@PathVariable String userId) {
        return ResponseEntity.ok(paymentService.getPaymentHistoryByUser(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));
    }

    // Xử lý callback thủ công (hoặc nếu webhook gọi endpoint này)
    @PostMapping("/paypal/callback")
    public ResponseEntity<Void> handlePayPalCallback(@RequestParam String paymentId, @RequestParam String payerId) {
        paymentService.handlePaymentCallback(paymentId, payerId);
        return ResponseEntity.ok().build();
    }
    // Huỷ payment khi đang pending
    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<Void> cancelPendingPayment(@PathVariable String paymentId) {
        paymentService.cancelPendingPayment(paymentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<PaymentResponse>> getAllPayment(){
        return ResponseEntity.ok().body(paymentService.getAllPayment());
    }

    @GetMapping("/vnpay/callback")
    public String handleVnPayCallback(@RequestParam Map<String, String> allParams) {
        return paymentService.handleVnPayCallback(allParams);
    }
    @GetMapping("/momo/callback")
    public String handleMomoCallback(@RequestParam Map<String, String> params) {
        return paymentService.handleMoMoCallback(params);
    }
}
