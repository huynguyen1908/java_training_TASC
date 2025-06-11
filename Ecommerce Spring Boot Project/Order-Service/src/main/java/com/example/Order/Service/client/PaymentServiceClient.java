//package com.example.Order.Service.client;
//
//
//import com.example.Order.Service.dto.request.PaymentRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(name = "PAYMENT-SERVICE")
//public interface PaymentServiceClient {
//    @PostMapping("/api/payment/create")
//    String createPayment(@RequestBody PaymentRequest paymentRequest);
//}
