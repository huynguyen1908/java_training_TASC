package com.example.Product.Service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetProductRequest {
    private String name;
    private String description;
    private String image;
    private String brand;
    private double price;
    private double discountPrice;
    private int discount;
}
