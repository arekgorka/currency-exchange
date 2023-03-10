package com.kantor.service;

import com.kantor.domain.BuyOrSell;
import com.kantor.domain.Order;
import com.kantor.domain.OrderStatus;
import com.kantor.domain.dto.OrderDto;
import com.kantor.domain.dto.TransactionDto;
import com.kantor.exception.*;
import com.kantor.mapper.OrderMapper;
import com.kantor.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Scheduled(cron = "0 0/5 * * * *")
    public void completeOrders() throws CurrencyNotFoundException {
        List<Order> orderList = orderRepository.findAll();
        List<Order> orders = orderList.stream()
                .filter(order -> order.getOrderStatus().equals(OrderStatus.ORDERED))
                .collect(Collectors.toList());
        for(Order order : orders) {
            if (order.getBuyOrSell().equals(BuyOrSell.BUY)) {
                double buy = transactionService.getBuyForAnyCurrencyFromRepository(order.getCurFrom());
                if (order.getExchangeRate() == buy) {
                    TransactionDto transactionDto = new TransactionDto(
                            order.getBuyOrSell(),
                            order.getCurFrom(),
                            order.getQtyFrom(),
                            order.getCurTo()
                    );
                    try {
                        transactionService.createBuyTransaction(order.getUser().getId(),transactionDto);
                        orderRepository.updateOrderStatus(order.getId(), OrderStatus.DONE);
                    } catch (UserNotFoundException | AccountBallanceNotFoundException | WrongValidationException e) {
                        orderRepository.updateOrderStatus(order.getId(), OrderStatus.CANCELLED);
                    }
                }
            }
            if (order.getBuyOrSell().equals(BuyOrSell.SELL)) {
                double sell = transactionService.getSellForAnyCurrencyFromRepository(order.getCurFrom());
                if (order.getExchangeRate() == sell) {
                    TransactionDto transactionDto = new TransactionDto(
                            order.getBuyOrSell(),
                            order.getCurFrom(),
                            order.getQtyFrom(),
                            order.getCurTo()
                    );
                    try {
                        transactionService.createSellTransaction(order.getUser().getId(),transactionDto);
                        orderRepository.updateOrderStatus(order.getId(), OrderStatus.DONE);
                    } catch (UserNotFoundException | AccountBallanceNotFoundException | WrongValidationException e) {
                        orderRepository.updateOrderStatus(order.getId(), OrderStatus.CANCELLED);
                    }
                }
            }
        }
    }

}
