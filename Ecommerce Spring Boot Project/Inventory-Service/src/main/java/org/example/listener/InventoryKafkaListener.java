package org.example.listener;

import org.example.entity.Inventory;
import org.example.event.ProductCreatedEvent;
import org.example.repository.InventoryRepository;
import org.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InventoryKafkaListener {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "product-created-topic", groupId = "inventory-group")
    public void handleProductCreated(ProductCreatedEvent event) {
        inventoryService.addStock(event.getSkuCode(), event.getName(), event.getQuantity(), event.getImportPrice());
    }
}
