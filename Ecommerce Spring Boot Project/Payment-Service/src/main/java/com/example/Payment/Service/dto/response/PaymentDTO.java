package com.example.Payment.Service.dto.response;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class PaymentDTO {
    private String paymentId;
    private String paymentMethod;
    private String status;
    private LocalDateTime date;
    private String transactionId;
    private String orderId;
}
