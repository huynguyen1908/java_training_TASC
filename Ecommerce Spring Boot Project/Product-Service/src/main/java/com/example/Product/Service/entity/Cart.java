package com.example.Product.Service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Type;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String cartId;
    @Column(name = "user_id")
    String userId;

    int quantity;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Product> products;

    public void addProduct(Product product) {
        product.setCart(this);
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        if (this.products != null) {
            product.setCart(this);
            this.products.remove(product);
        }
    }
}
