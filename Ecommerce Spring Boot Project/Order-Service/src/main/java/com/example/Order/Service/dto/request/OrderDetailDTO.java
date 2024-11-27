package com.example.Order.Service.dto.request;

import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
public class OrderDetailDTO {
    String order_id;
    String product_id;
    int quantity;
    double total_price;
    double discount_price;

    @Override
    public String toString(){
        return "Order id: " + order_id + "\n"
                + "Product id: " + product_id + "\n"
                + "Quantity of product" + quantity + "\n"
                + "Total amount" + total_price + "\n"
                + "Discount price" + discount_price;
    }
}
