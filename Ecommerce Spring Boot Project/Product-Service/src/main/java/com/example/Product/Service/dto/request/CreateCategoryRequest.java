package com.example.Product.Service.dto.request;

import com.example.Product.Service.entity.Product;
import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCategoryRequest {
    String name;
    String description;
    LocalDateTime createdDate;
    String createdBy;
}
