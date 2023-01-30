package com.kantor.controller;

import com.kantor.domain.dto.OrderDto;
import com.kantor.exception.OrderNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/currency_exchange/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @RequestBody OrderDto orderDto) throws UserNotFoundException {
        return ResponseEntity.ok(orderService.createOrder(userId, orderDto));
    }

    @PutMapping(value = "{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) throws OrderNotFoundException {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderDto));
    }

    @DeleteMapping(value = "{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping(value = "/ordersHistory/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAllOrdersByUserId(userId));
    }

    @GetMapping(value = "/currentOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getCurrentOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getCurrentOrdersByUserId(userId));
    }
}
