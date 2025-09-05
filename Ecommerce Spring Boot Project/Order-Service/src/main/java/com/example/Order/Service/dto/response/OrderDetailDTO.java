package com.example.Order.Service.dto.response;

import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

@Data
public class OrderDetailDTO {
    String orderDetailId;
    String skuCode;
    String productName;
    int quantity;
    double price;
}
