package com.springdemo.orderservice.service;

import com.springdemo.orderservice.config.WebClientConfig;
import com.springdemo.orderservice.dto.OrderLineItemsDto;
import com.springdemo.orderservice.dto.OrderRequestDto;
import com.springdemo.orderservice.model.Order;
import com.springdemo.orderservice.model.OrderLineItems;
import com.springdemo.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequestDto orderRequestDto) {
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        //we are getting orderRequestDto object but we need OrderLineItems object so we need to map
        List<OrderLineItems> listoforders = orderRequestDto.getOrderLineItemsDto().stream()
                .map(this::mapToDto).toList();
        order.setOrderLineItems(listoforders);

        //before placing order check if order is inStock in Inventory Stock
        Boolean result = webClient.get().uri("http://inventory-service/api/inventory/")
                .retrieve().bodyToMono(Boolean.class)
                .block();
        if(Boolean.TRUE.equals(result)) {
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems; 
    }
}
