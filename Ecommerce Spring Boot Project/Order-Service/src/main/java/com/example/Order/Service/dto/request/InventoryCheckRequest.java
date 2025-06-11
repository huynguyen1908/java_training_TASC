package com.example.Order.Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCheckRequest {
    private String skuCode;
    private int quantity;
}
