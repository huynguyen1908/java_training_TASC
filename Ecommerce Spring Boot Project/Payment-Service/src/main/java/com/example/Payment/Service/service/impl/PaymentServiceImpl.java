package com.example.Payment.Service.service.impl;

import com.example.Payment.Service.dto.request.CreatePaymentRequest;
import com.example.Payment.Service.entity.Payment;
import com.example.Payment.Service.mapper.PaymentMapper;
import com.example.Payment.Service.repository.PaymentRepository;
import com.example.Payment.Service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public Payment createPayment(CreatePaymentRequest createPaymentRequest) {
        Payment payment = paymentMapper.toPayment(createPaymentRequest);
        payment.setStatus("PENDING");
        payment.setPaymentMethod("PAYPAL");
        payment.setPaymentId(createPaymentRequest.getOrderId());

        return paymentRepository.save(payment);

    }

}
