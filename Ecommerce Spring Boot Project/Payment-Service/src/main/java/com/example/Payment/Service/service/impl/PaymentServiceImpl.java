package com.example.Payment.Service.service.impl;

import com.example.Payment.Service.entity.Payment;
import com.example.Payment.Service.repository.PaymentRepository;
import com.example.Payment.Service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public String createPayment(Payment payment) {
        payment.setStatus("COMPLETED"); // Giả lập thanh toán thành công
        payment.setDate(LocalDateTime.now());

        paymentRepository.save(payment);

        return "SUCCESS";
    }

}
