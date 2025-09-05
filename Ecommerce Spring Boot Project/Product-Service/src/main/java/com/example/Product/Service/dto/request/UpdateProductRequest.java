package com.example.Product.Service.dto.request;

import com.example.Product.Service.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class UpdateProductRequest {
    private String name;
    private String description;
//    private List<MultipartFile> images;
    private String brand;
    private double price;
    private int discount;
    private String skuCode;
    private String categoryId;
    private int quantity;
    private LocalDateTime updatedDate;
    private String updatedBy;
}
