package com.example.Product.Service.dto.request;

import com.example.Product.Service.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private String image;
    private String brand;
    private double price;
    private int discount;
    private LocalDateTime updatedDate;
    private String updatedBy;
}
