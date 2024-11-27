package com.example.Product.Service.dto.request;

import com.example.Product.Service.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveFromCartRequest {
    String cartId;
    Product product;
}
