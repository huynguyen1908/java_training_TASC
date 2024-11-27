package com.example.Product.Service.mapper;

import com.example.Product.Service.dto.request.CreateProductRequest;
import com.example.Product.Service.dto.request.UpdateProductRequest;
import com.example.Product.Service.entity.Product;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
//import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;


@Component
@RequiredArgsConstructor
@Builder
public class ProductMapper {
    public Product toProduct(CreateProductRequest request){
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .description(request.getDescription())
                .image(request.getImage())
                .importPrice(request.getImportPrice())
                .discountPrice(request.getDiscountPrice())
                .discount(request.getDiscount())
                .createdDate(LocalDateTime.now())
                .createdBy(request.getCreatedBy())
                .build();
    }
    public Product toUpdatedProduct(UpdateProductRequest request){
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .description(request.getDescription())
                .image(request.getImage())
                .importPrice(request.getImportPrice())
                .discountPrice(request.getDiscountPrice())
                .discount(request.getDiscount())
                .updatedDate(LocalDateTime.now())
                .updatedBy(request.getUpdatedBy())
                .build();
    }
}
