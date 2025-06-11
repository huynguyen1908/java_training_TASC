package com.example.Payment.Service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "paypal")
@Data
public class PayPalProperties {
    private String clientId;
    private String clientSecret;
    private String baseUrl;
    private String returnUrl;
    private String cancelUrl;
}
