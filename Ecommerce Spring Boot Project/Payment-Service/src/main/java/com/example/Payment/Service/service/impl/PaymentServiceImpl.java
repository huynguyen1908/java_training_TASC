package com.example.Payment.Service.service.impl;

import com.example.Payment.Service.client.MoMoClient;
import com.example.Payment.Service.client.PayPalClient;
import com.example.Payment.Service.client.VNPayClient;
import com.example.Payment.Service.config.QRCodeGenerator;
import com.example.Payment.Service.dto.request.CreatePaymentRequest;
import com.example.Payment.Service.dto.request.PayPalExecuteRequest;

import com.example.Payment.Service.dto.response.CreatePaymentResponse;
import com.example.Payment.Service.dto.response.PaymentResponse;
import com.example.Payment.Service.dto.response.RefundResponse;
import com.example.Payment.Service.entity.Payment;
import com.example.Payment.Service.enums.PaymentGateway;
import com.example.Payment.Service.enums.PaymentStatus;
import com.example.Payment.Service.event.PaymentNotificationEvent;
import com.example.Payment.Service.mapper.PaymentMapper;
import com.example.Payment.Service.repository.PaymentRepository;
import com.example.Payment.Service.service.PaymentService;
import jakarta.ws.rs.NotFoundException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;

    @Autowired
    private PayPalClient payPalClient;

    @Autowired
    private VNPayClient vnPayClient;

    @Autowired
    private MoMoClient moMoClient;

    @Value("${vnpay.hashSecret}")
    private String vnpHashSecret;

    @Autowired
    private KafkaTemplate<String, PaymentNotificationEvent> notificationEventKafkaTemplate;

    @Override
    public CreatePaymentResponse createPayPalPayment(CreatePaymentRequest request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .gateway(request.getGateway())
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .userId(request.getUserId())
                .build();

        payment = paymentRepository.save(payment);

        String approvalUrl = "";
        if (request.getGateway().equals(PaymentGateway.PAYPAL)){
            approvalUrl = payPalClient.createPayment(payment);
        } else if(request.getGateway().equals(PaymentGateway.VNPAY)){
            try {
                approvalUrl = vnPayClient.createPaymentUrl(payment);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } else if(request.getGateway().equals(PaymentGateway.MOMO)){
            approvalUrl = moMoClient.createPayment(payment);
        }

        String base64Qr;
        try {
            byte[] qrBytes = QRCodeGenerator.generateQRCodeImage(approvalUrl, 300, 300);
            base64Qr = Base64.getEncoder().encodeToString(qrBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }

        PaymentNotificationEvent event = new PaymentNotificationEvent(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getResponseCode(),
                payment.getResponseMessage(),
                "Order:" + payment.getOrderId() + "has been " + payment.getStatus()
        );
        notificationEventKafkaTemplate.send("payment-notification-event", event);
        return new CreatePaymentResponse(payment.getPaymentId(), approvalUrl, base64Qr);
    }

    @Override
    public String executePayPalPayment(PayPalExecuteRequest request) {
        boolean success = payPalClient.executePayment(request.getPaymentId(), request.getPayerId());

        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        payment.setPaidAt(success ? LocalDateTime.now() : null);

        paymentRepository.save(payment);
        return success ? "Payment successful" : "Payment failed";
    }


    @Override
    public PaymentResponse getPaymentDetail(String id) {
        return paymentMapper.toResponse(findById(id));
    }

    @Override
    public List<PaymentResponse> getPaymentHistoryByUser(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse getPaymentByOrder(String orderId) {
        return paymentMapper.toResponse(paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Payment not found for order: " + orderId)));
    }

    @Override
    public RefundResponse refundPayment(String paymentId) {
        Payment payment = findById(paymentId);

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Only successful payments can be refunded.");
        }

        boolean success = payPalClient.refundPayment(payment.getProviderTransactionId());
        if (success) {
            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setRefundedAt(LocalDateTime.now());
            paymentRepository.save(payment);
        }

        return RefundResponse.builder()
                .paymentId(paymentId)
                .success(success)
                .refundedAt(payment.getRefundedAt())
                .message(success ? "Refund successful" : "Refund failed")
                .build();
    }

    @Override
    public void handlePaymentCallback(String paymentId, String payerId) {
        Payment payment = findById(paymentId);
        boolean executed = payPalClient.executePayment(paymentId, payerId);

        if (executed) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setProviderTransactionId(paymentId);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
    }

    @Override
    public void cancelPendingPayment(String paymentId) {
        Payment payment = findById(paymentId);
        if (payment.getStatus() == PaymentStatus.PENDING) {
            payment.setStatus(PaymentStatus.CANCELLED);
            paymentRepository.save(payment);
        } else {
            throw new IllegalStateException("Only pending payments can be cancelled.");
        }
    }

    @Override
    public Payment findById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));
    }


    @Override
    public List<PaymentResponse> getAllPayment(){
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public String handleVnPayCallback(Map<String, String> allParams) {
        String vnpSecureHash = allParams.remove("vnp_SecureHash");
        allParams.remove("vnp_SecureHashType"); // Optional

        Map<String, String> sortedParams = new TreeMap<>(allParams);
        String signData = sortedParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        String generatedHash = hmacSHA512(vnpHashSecret, signData);

        if (!generatedHash.equals(vnpSecureHash)) {
            return "INVALID SIGNATURE";
        }

        String txnRef = allParams.get("vnp_TxnRef");
        String responseCode = allParams.get("vnp_ResponseCode");
        String transactionNo = allParams.get("vnp_TransactionNo");

        Optional<Payment> optionalPayment = paymentRepository.findById(txnRef);
        if (optionalPayment.isEmpty()) {
            return "PAYMENT NOT FOUND";
        }

        Payment payment = optionalPayment.get();

        boolean success = "00".equals(responseCode);
        payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        payment.setSuccess(success);
        payment.setResponseCode(responseCode);
        payment.setResponseMessage(allParams.getOrDefault("vnp_Message", ""));
        payment.setProviderTransactionId(transactionNo);
        payment.setPaidAt(LocalDateTime.now());
        payment.setRedirectUrl(allParams.get("vnp_ReturnUrl"));

        payment.setExtraData(allParams.toString());
        paymentRepository.save(payment);

        return "PAYMENT " + payment.getStatus().name();
    }

    private String hmacSHA512(String key, String data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate HMAC SHA512", e);
        }
    }

    @Override
    public String handleMoMoCallback(Map<String, String> params) {
        String orderId = params.get("orderId");
        String resultCode = params.get("resultCode");

        Optional<Payment> optionalPayment = paymentRepository.findById(orderId);
        if (optionalPayment.isEmpty()) return "NOT FOUND";

        Payment payment = optionalPayment.get();
        if ("0".equals(resultCode)) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            payment.setSuccess(true);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
        return "PAYMENT " + payment.getStatus().name();
    }

}
