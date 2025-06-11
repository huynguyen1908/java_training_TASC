package com.example.Order.Service.client;

import com.example.Order.Service.dto.request.InventoryCheckRequest;
import com.example.Order.Service.dto.request.StockUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {
    @PostMapping("/api/inventory/check-stock")
    boolean isInStock(@RequestBody List<InventoryCheckRequest> items);

    @PutMapping("/api/inventory/update-stock")
    void updateStockQuantity(@RequestBody List<StockUpdateRequest> items, @RequestParam boolean isOrderCancelled);
}