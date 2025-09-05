package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    public Inventory(String skuCode) {
        this.skuCode = skuCode;
    }

    public Inventory(String skuCode, String name, int quantity, double importPrice, LocalDateTime importedAt) {
        this.skuCode = skuCode;
        this.name = name;
        this.quantity = quantity;
        this.importPrice = importPrice;
        this.importedAt = importedAt;
    }

    @Id
    String skuCode;
    String name;
    int quantity;
    int reservedQuantity;
    double importPrice;
    LocalDateTime importedAt;
}
