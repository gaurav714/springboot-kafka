package com.springdemo.orderservice.controller;

import com.springdemo.orderservice.dto.OrderRequestDto;
import com.springdemo.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
    System.out.println("the object is "+ orderRequestDto.getOrderLineItemsDto());
    orderService.placeOrder(orderRequestDto);
        return "Order placed successfully";
    }
}
