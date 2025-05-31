package com.example.Product.Service.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private String cartId;
    private String userId;
    private List<CartItemDTO> items;
}
