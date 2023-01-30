package com.kantor.service;

import com.kantor.domain.Order;
import com.kantor.domain.OrderStatus;
import com.kantor.domain.User;
import com.kantor.domain.dto.OrderDto;
import com.kantor.exception.OrderNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.mapper.OrderMapper;
import com.kantor.repository.OrderRepository;
import com.kantor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class OrderService {

    private final TransactionService transactionService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    public OrderDto createOrder(Long userId, OrderDto orderDto) throws UserNotFoundException {
        Order order = orderMapper.mapToOrder(userId, orderDto);
        orderRepository.save(order);
        return orderMapper.mapToOrderDto(order);
    }

    public OrderDto updateOrder(Long orderId, OrderDto orderDto) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Order upadtedOrder = new Order(
                orderId,
                order.getUser(),
                order.getDatetime(),
                orderDto.getBuyOrSell(),
                orderDto.getCurFrom(),
                orderDto.getQtyFrom(),
                orderDto.getCurTo(),
                orderDto.getExchangeRate(),
                order.getOrderStatus()
        );
        orderRepository.deleteById(orderId);
        orderRepository.save(upadtedOrder);
        return orderMapper.mapToOrderDto(upadtedOrder);
    }

    public void deleteOrder(final Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public OrderDto getOrder(final Long orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return orderMapper.mapToOrderDto(order);
    }

    public List<OrderDto> getAllOrdersByUserId(final Long userId) {
        List<Order> orderList = orderRepository.findOrdersByUserId(userId);
        return orderMapper.mapToListOrderDto(orderList);
    }

    public List<OrderDto> getCurrentOrdersByUserId(Long userId) {
        List<Order> orderList = orderRepository.findOrdersByUserId(userId);
        List<Order> orders = orderList.stream()
                .filter(order -> order.getOrderStatus().equals(OrderStatus.ORDERED))
                .collect(Collectors.toList());
        return orderMapper.mapToListOrderDto(orders);
    }

    @Scheduled(cron = "* 0/5 * * * *")
    public void completeOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<Order> orders = orderList.stream()
                .filter(order -> order.getOrderStatus().equals(OrderStatus.ORDERED))
                .collect(Collectors.toList());
        for(Order order : orders) {
            //LOGIKA
        }
    }

}
