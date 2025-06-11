package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.InventoryCheckRequest;
import org.example.dto.request.StockUpdateRequest;
import org.example.entity.Inventory;
import org.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/check-stock")
    public ResponseEntity<Boolean> isInStock(@RequestBody List<InventoryCheckRequest> requests) {
        return ResponseEntity.ok(inventoryService.isInStock(requests));
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateStock(@RequestBody List<StockUpdateRequest> request,
            @RequestParam boolean isOrderCancelled
    ) {
        inventoryService.updateStockQuantity(request, isOrderCancelled);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-stock")
    public ResponseEntity<Void> addStock(@RequestBody Inventory inventory) {
        inventoryService.addStock(inventory.getSkuCode(), inventory.getName(), inventory.getQuantity(), inventory.getImportPrice());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<Inventory> getStockByProduct(@PathVariable String skuCode) {
        return ResponseEntity.ok(inventoryService.getStockByProduct(skuCode));
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }
}

