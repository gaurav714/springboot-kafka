package com.springdemo.orderservice.service;
import com.springdemo.orderservice.dto.InventoryResponseDto;
import com.springdemo.orderservice.dto.OrderLineItemsDto;
import com.springdemo.orderservice.dto.OrderRequestDto;
import com.springdemo.orderservice.model.Order;
import com.springdemo.orderservice.model.OrderLineItems;
import com.springdemo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final  WebClient webClient;

    public void placeOrder(OrderRequestDto orderRequestDto) {
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        //we are getting orderRequestDto object, but we need OrderLineItems object, so we need to map
        List<OrderLineItems> listoforders = orderRequestDto.getOrderLineItemsDto().stream()
                .map(this::mapToDto).toList();
        order.setOrderLineItems(listoforders);

        List<String> skuCodes = order.getOrderLineItems().stream().map(this::getSkucode).toList();

        //before placing order check if order is inStock in Inventory Stock

        //Very important feature of url builder
        //sending List of skuCode as a query parameter
        InventoryResponseDto[] invRespoArr = webClient.get().uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponseDto[].class)
                .block();

        //instead using for loop use java8 stream
        boolean AreAllProductsInStock = Arrays.stream(invRespoArr).allMatch(InventoryResponseDto::isInStock);
        if(Boolean.TRUE.equals(AreAllProductsInStock)) {
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock");
        }

    }

    private String getSkucode(OrderLineItems orderLineItems) {
        return orderLineItems.getSkuCode();
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems; 
    }
}
