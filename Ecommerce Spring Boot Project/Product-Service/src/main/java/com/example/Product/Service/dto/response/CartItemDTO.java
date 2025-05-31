package com.example.Product.Service.dto.response;

import com.example.Product.Service.entity.Product;
import lombok.Data;

@Data
public class CartItemDTO {
    private String cartItemId;
    private ProductDTO product;
    private int quantity;
}
