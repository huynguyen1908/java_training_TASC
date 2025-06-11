package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentNotificationEvent {
    private String paymentId;
    private String userId;
    private String orderId;
    private BigDecimal amount;
    private String responseCode;
    private String responseMessage;
    private String content;
}
