package org.example.listener;

import org.example.entity.Inventory;
import org.example.event.ProductCreatedEvent;
import org.example.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryKafkaListener {

    @Autowired
    private InventoryRepository inventoryRepository;

    @KafkaListener(topics = "product-created-topic", groupId = "inventory-group")
    public void handleProductCreated(ProductCreatedEvent event) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(event.getSkuCode());
        inventory.setName(event.getName());
        inventory.setQuantity(0);
        inventory.setImportPrice(event.getImportPrice());
        inventoryRepository.save(inventory);
    }
}
