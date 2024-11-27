package com.example.Product.Service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductRequest {
    private String name;
    private String description;
    private String image;
    private String brand;
    private double importPrice;
    private double price;
    private double discountPrice;
    private int discount;
    private LocalDateTime createdDate;
    private String createdBy;
}
