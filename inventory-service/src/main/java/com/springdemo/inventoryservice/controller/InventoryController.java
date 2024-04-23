package com.springdemo.inventoryservice.controller;

import com.springdemo.inventoryservice.dto.InventoryResponseDto;
import com.springdemo.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    //for one sku code
//    private final InventoryService inventoryService;
//    @GetMapping("/{skuCode}")
//    @ResponseStatus(HttpStatus.OK)
//    public Boolean isInStock(@PathVariable String skuCode) {
//
//        return inventoryService.isInStock(skuCode);
//
//    }

    //for multiple sku codes
private final InventoryService inventoryService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto>isInStock(@RequestParam List<String> skuCodes) {

        return inventoryService.isInStock(skuCodes);

    }
}
