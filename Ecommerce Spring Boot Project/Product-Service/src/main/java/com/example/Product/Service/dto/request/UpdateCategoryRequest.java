package com.example.Product.Service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCategoryRequest {
    String name;
    String description;
    LocalDateTime updatedDate;
    String updatedBy;
}
