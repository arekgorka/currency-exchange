package com.kantor.mapper;

import com.kantor.domain.Order;
import com.kantor.domain.dto.OrderDto;
import com.kantor.exception.UserNotFoundException;
import com.kantor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final UserService userService;

    public Order mapToOrder(Long userId, OrderDto orderDto) throws UserNotFoundException {
        return new Order(
                userService.findUserById(userId),
                orderDto.getBuyOrSell(),
                orderDto.getCurFrom(),
                orderDto.getQtyFrom(),
                orderDto.getCurTo(),
                orderDto.getExchangeRate()
        );
    }

    public OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getDatetime(),
                order.getBuyOrSell(),
                order.getCurFrom(),
                order.getQtyFrom(),
                order.getCurTo(),
                order.getExchangeRate()
        );
    }

    public List<OrderDto> mapToListOrderDto (List<Order> orderList) {
        return orderList.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
}
