package com.example.Product.Service.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private String image;
    private String brand;
    private double importPrice;
    private double price;
    private double discountPrice;
    private int discount;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
