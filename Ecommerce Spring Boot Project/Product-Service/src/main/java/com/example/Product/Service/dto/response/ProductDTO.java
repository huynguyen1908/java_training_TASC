package com.example.Product.Service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {
    private String productId;
    private String name;
    private String description;
    private List<String> imageUrl;
    private String brand;
    private double importPrice;
    private double price;
    private int discount;
    private String skuCode;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String category;
}
