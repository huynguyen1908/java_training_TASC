package com.example.Product.Service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    private int id;
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
