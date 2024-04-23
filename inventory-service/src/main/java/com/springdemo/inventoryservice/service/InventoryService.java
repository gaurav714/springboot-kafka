package com.springdemo.inventoryservice.service;

import com.springdemo.inventoryservice.dto.InventoryResponseDto;
import com.springdemo.inventoryservice.model.Inventory;
import com.springdemo.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    //@Transactional(readOnly = true)
    //for one sku code
//    public boolean isInStock(String skuCode) {
//        return inventoryRepository.findByskuCode(skuCode).isPresent();
//
//    }
    //for multiple sku codes
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> isInStock(List<String> skuCodes) {
        return inventoryRepository.findByskuCodeIn(skuCodes).stream()
                .map(this::mapper).toList();


    }
//WE need to check if the quantity product of the given skuCode is greater than 0
    private InventoryResponseDto mapper(Inventory inventory) {

        return InventoryResponseDto.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build();
    }
}
