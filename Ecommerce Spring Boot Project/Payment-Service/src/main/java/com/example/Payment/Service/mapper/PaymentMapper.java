package com.example.Payment.Service.mapper;

import com.example.Payment.Service.dto.request.CreatePaymentRequest;
import com.example.Payment.Service.dto.request.PaymentDTO;
import com.example.Payment.Service.entity.Payment;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    Payment toPayment(CreatePaymentRequest createPaymentRequest);
    PaymentDTO toPaymentDTO(Payment payment);
}
