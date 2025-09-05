package org.example.repository;

import org.example.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findBySkuCode(String skuCode);

    @Query(value = "select quantity from inventory where sku_code = :skuCode", nativeQuery = true)
    Optional<Integer> getQuantityBySkuCode(String skuCode);
}
