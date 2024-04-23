package com.springdemo.inventoryservice.repository;

import com.springdemo.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional <Inventory> findByskuCode(String skuCode);
}
