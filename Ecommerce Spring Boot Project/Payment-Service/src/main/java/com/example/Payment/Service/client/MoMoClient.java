package com.example.Payment.Service.client;

import com.example.Payment.Service.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MoMoClient {
    @Value("${momo.partnerCode}")
    private String partnerCode;

    @Value("${momo.accessKey}")
    private String accessKey;

    @Value("${momo.secretKey}")
    private String secretKey;

    @Value("${momo.returnUrl}")
    private String returnUrl;

    @Value("${momo.notifyUrl}")
    private String notifyUrl;

    @Value("${momo.endpoint}")
    private String endpoint;

    public String createPayment(Payment payment) {
        try {
            String requestId = UUID.randomUUID().toString();
            String orderId = payment.getPaymentId();

            String rawHash = "accessKey=" + accessKey +
                    "&amount=" + payment.getAmount().toString() +
                    "&extraData=" +
                    "&ipnUrl=" + notifyUrl +
                    "&orderId=" + orderId +
                    "&orderInfo=Thanh toan don hang " + payment.getOrderId() +
                    "&partnerCode=" + partnerCode +
                    "&redirectUrl=" + returnUrl +
                    "&requestId=" + requestId +
                    "&requestType=captureWallet";

            String signature = hmacSHA256(rawHash, secretKey);

            Map<String, Object> body = new HashMap<>();
            body.put("partnerCode", partnerCode);
            body.put("accessKey", accessKey);
            body.put("requestId", requestId);
            body.put("amount", payment.getAmount().toString());
            body.put("orderId", orderId);
            body.put("orderInfo", "Thanh toan don hang " + payment.getOrderId());
            body.put("redirectUrl", returnUrl);
            body.put("ipnUrl", notifyUrl);
            body.put("extraData", "");
            body.put("requestType", "captureWallet");
            body.put("signature", signature);
            body.put("lang", "vi");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);
            return (String) response.getBody().get("payUrl");
        } catch (Exception e) {
            throw new RuntimeException("Error creating MoMo payment", e);
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}

