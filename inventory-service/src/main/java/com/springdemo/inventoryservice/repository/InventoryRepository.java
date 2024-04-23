package com.springdemo.inventoryservice.repository;

import com.springdemo.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    //for one skucode
    // Optional <Inventory> findByskuCode(String skuCode);
    List<Inventory> findByskuCodeIn(List<String> skuCode);
}
