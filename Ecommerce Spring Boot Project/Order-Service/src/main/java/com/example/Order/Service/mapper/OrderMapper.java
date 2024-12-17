package com.example.Order.Service.mapper;

import com.example.Order.Service.dto.request.OrderDTO;
import com.example.Order.Service.entity.Order;
import lombok.Data;
import org.mapstruct.Mapper;


@Mapper
public interface OrderMapper {
    OrderDTO toOrderDTO(Order order);
    Order toOrder(OrderDTO orderDTO);
}
