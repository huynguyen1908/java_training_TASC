package org.example.service;

import org.example.dto.request.InventoryCheckRequest;
import org.example.dto.request.StockUpdateRequest;
import org.example.entity.Inventory;
import org.example.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

//    public boolean isInStock(String skuCode, int requestedQuantity) {
//        return inventoryRepository.findBySkuCode(skuCode)
//                .filter(inv -> inv.getQuantity() >= requestedQuantity)
//                .map(inv -> {
//                    inv.setQuantity(inv.getQuantity() - requestedQuantity);
//                    inv.setReservedQuantity(inv.getReservedQuantity() + requestedQuantity);
//                    inventoryRepository.save(inv);
//                    return true;
//                })
//                .orElse(false);
//    }

    public boolean isInStock(List<InventoryCheckRequest> requests){
        for (InventoryCheckRequest item: requests) {
            Inventory inventory = inventoryRepository.findBySkuCode(item.getSkuCode()).orElseThrow(()->new RuntimeException("Inventory not found:" + item.getSkuCode()));
            if (inventory.getQuantity() == 0 || inventory.getQuantity() < item.getQuantity()){
                System.out.println("This product is not enough");
                return false;
            }
        }
        return true;
    }

    public void updateStockQuantity(List<StockUpdateRequest> request, boolean isOrderCancelled) {
        for (StockUpdateRequest item: request) {
            inventoryRepository.findBySkuCode(item.getSkuCode()).ifPresent(inv -> {
                if (isOrderCancelled) {
                    inv.setQuantity(inv.getQuantity() + item.getQuantity());
                    inv.setReservedQuantity(Math.max(0, inv.getReservedQuantity() - item.getQuantity()));
                } else {
                    inv.setQuantity(inv.getQuantity() - item.getQuantity());
                    inv.setReservedQuantity(Math.max(0, inv.getReservedQuantity() + item.getQuantity()));
                }
                inventoryRepository.save(inv);
            });
        }
    }

    public void addStock(String skuCode, String name, int quantity, double importPrice) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(()-> new RuntimeException("Not found inventory"));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventory.setImportPrice(importPrice);
        inventory.setName(name);
        inventory.setImportedAt(LocalDateTime.now());
        inventoryRepository.save(inventory);
    }

    public Inventory getStockByProduct(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

}
