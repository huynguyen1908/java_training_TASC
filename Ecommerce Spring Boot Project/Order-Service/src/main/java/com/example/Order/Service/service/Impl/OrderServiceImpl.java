package com.example.Order.Service.service.Impl;

import com.example.Order.Service.client.InventoryClient;
//import com.example.Order.Service.client.PaymentServiceClient;
import com.example.Order.Service.dto.request.InventoryCheckRequest;
import com.example.Order.Service.dto.request.PaymentRequest;
import com.example.Order.Service.dto.request.StockUpdateRequest;
import com.example.Order.Service.dto.response.OrderDTO;
import com.example.Order.Service.entity.Order;
import com.example.Order.Service.entity.OrderDetail;
import com.example.Order.Service.enums.Status;
import com.example.Order.Service.event.OrderCreatedEvent;
import com.example.Order.Service.mapper.OrderMapper;
import com.example.Order.Service.repository.OrderRepository;
import com.example.Order.Service.service.OrderService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    private KafkaTemplate<String, OrderCreatedEvent> orderCreatedEventKafkaTemplate;
    @Override
    @Transactional
    public void placeOrder(OrderDTO orderDTO) {
        List<InventoryCheckRequest> checkRequests = orderDTO.getOrderDetailList().stream()
                .map(item -> new InventoryCheckRequest(item.getSkuCode(), item.getQuantity()))
                .collect(Collectors.toList());
        boolean inStock = inventoryClient.isInStock(checkRequests);
        if(!inStock) throw new RuntimeException("Some products are out of stock.");

        Order order = orderMapper.toOrder(orderDTO);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        for (OrderDetail detail : order.getOrderDetailList()) {
            detail.setOrder(order);
        }

        orderRepository.save(order);

        List<StockUpdateRequest> updateRequests = orderDTO.getOrderDetailList().stream()
                .map(item -> new StockUpdateRequest(item.getSkuCode(), item.getQuantity()))
                .collect(Collectors.toList());

        inventoryClient.updateStockQuantity(updateRequests, false);

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getUserId(),
                "Bạn đã đặt đơn hàng #" + order.getOrderId() + " thành công.",
                order.getOrderId()
        );
        orderCreatedEventKafkaTemplate.send("order-placed-topic", event);
    }

    @Override
    @Transactional
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        order.setStatus(Status.CANCELED);
        order.setShippedAt(null);
        orderRepository.save(order);
        List<StockUpdateRequest> requests = order.getOrderDetailList().stream()
                        .map(item -> new StockUpdateRequest(item.getSkuCode(), item.getQuantity()))
                        .collect(Collectors.toList());

        inventoryClient.updateStockQuantity(requests, true);
    }

    @Override
    public List<OrderDTO> getOrderHistoryByUser(String userId){
        return orderRepository.findByUserId(userId).stream()
                .map(order -> orderMapper.toDTO(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderDetails(String orderId){
        return orderRepository.findById(orderId).map(orderMapper::toDTO)
                .orElseThrow(()->new RuntimeException("Order not found" + orderId));
    }

    @Override
    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderId, String status){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        try {
            order.setStatus(Status.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        if (order.getStatus() == Status.SHIPPED) {
            order.setShippedAt(LocalDateTime.now());
        }

        orderRepository.save(order);
    }
}
