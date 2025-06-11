package com.example.Payment.Service.client;

import com.example.Payment.Service.config.PayPalProperties;
import com.example.Payment.Service.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PayPalClient {
    @Autowired
    private PayPalProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    private String accessToken;

    public String createPayment(Payment payment) {
        ensureAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = buildCreatePaymentRequest(payment);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                properties.getBaseUrl() + "/v1/payments/payment",
                request,
                Map.class
        );

        // Parse approval URL
        List<Map<String, String>> links = (List<Map<String, String>>) response.getBody().get("links");
        for (Map<String, String> link : links) {
            if ("approval_url".equals(link.get("rel"))) {
                return link.get("href");
            }
        }

        throw new RuntimeException("Approval URL not found in PayPal response");
    }

    public boolean executePayment(String paymentId, String payerId) {
        ensureAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of("payer_id", payerId);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                properties.getBaseUrl() + "/v1/payments/payment/" + paymentId + "/execute",
                request,
                Map.class
        );

        return "approved".equalsIgnoreCase((String) response.getBody().get("state"));
    }

    private void ensureAccessToken() {
        if (accessToken == null || isTokenExpired()) {
            refreshAccessToken();
        }
    }

    private void refreshAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(properties.getClientId(), properties.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                properties.getBaseUrl() + "/v1/oauth2/token",
                request,
                Map.class
        );

        accessToken = (String) response.getBody().get("access_token");
    }

    private boolean isTokenExpired() {
        // Tạm thời: không check expired. Có thể lưu thêm expires_in và thời điểm tạo token.
        return false;
    }

    private Map<String, Object> buildCreatePaymentRequest(Payment payment) {
        String formattedAmount = payment.getAmount()
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();

        Map<String, Object> transaction = Map.of(
                "amount", Map.of(
                        "total", formattedAmount, // ✅ Đã chuẩn hóa đúng định dạng
                        "currency", payment.getCurrency()
                ),
                "description", "Payment for order " + payment.getOrderId()
        );

        return Map.of(
                "intent", "sale",
                "payer", Map.of("payment_method", "paypal"),
                "redirect_urls", Map.of(
                        "return_url", properties.getReturnUrl(),
                        "cancel_url", properties.getCancelUrl()
                ),
                "transactions", List.of(transaction)
        );
    }


    public boolean refundPayment(String providerTransactionId) {
        // chuẩn bị URL refund, có thể dựa trên providerTransactionId
        String url = properties.getBaseUrl() + "/v2/payments/captures/" + providerTransactionId + "/refund";

        // chuẩn bị body nếu cần (PayPal refund có thể yêu cầu amount)
        Map<String, Object> requestBody = new HashMap<>();
        // Nếu refund toàn bộ, có thể để trống hoặc truyền amount cụ thể

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ensureAccessToken();
        headers.setBearerAuth(accessToken);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            // Kiểm tra status code và response nếu cần
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            // Log lỗi hoặc xử lý theo nhu cầu
            return false;
        }
    }

}
