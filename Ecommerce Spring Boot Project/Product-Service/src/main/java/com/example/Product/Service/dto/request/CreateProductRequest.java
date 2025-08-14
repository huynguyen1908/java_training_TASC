package com.example.Product.Service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;
    private String description;

    @NotBlank(message = "Brand is required")
    private String brand;

    @Positive(message = "Price must be greater than 0")
    private double price;

    @Min(value = 0, message = "Discount must be between 0 and 100")
    @Max(value = 100, message = "Discount must be between 0 and 100")
    private int discount;
    private String skuCode;
    private String categoryId;
}
