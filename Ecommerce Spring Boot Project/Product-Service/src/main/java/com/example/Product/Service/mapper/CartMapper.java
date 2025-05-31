package com.example.Product.Service.mapper;

import com.example.Product.Service.dto.response.CartDTO;
import com.example.Product.Service.dto.response.CartItemDTO;
import com.example.Product.Service.entity.Cart;
import com.example.Product.Service.entity.CartItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "cartId", target = "cartId")
    CartDTO toDTO(Cart cart);
    Cart toCart(CartDTO cartDTO);
    CartItemDTO toCartItemDTO(CartItems item);
}
