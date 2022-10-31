package com.kantor.service;

import com.kantor.domain.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    public OrderDto createOrder(OrderDto orderDto) {
        return new OrderDto();
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        return new OrderDto();
    }

    public void deleteOrder(final Long orderId) {

    }

    public OrderDto getOrder(final Long orderId) {
        return new OrderDto();
    }

    public List<OrderDto> getOrders(final Long userId) {
        return new ArrayList<>();
    }
}
