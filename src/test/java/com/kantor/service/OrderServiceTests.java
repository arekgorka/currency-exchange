package com.kantor.service;

import com.kantor.domain.*;
import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.dto.OrderDto;
import com.kantor.exception.*;
import com.kantor.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTests {

    private final User user = new User("Michael","Jordan","mj2010","Mjmj","mj2010@gmail.com");
    private final Order orderBuy = new Order(user,BuyOrSell.BUY,"usd",100.0,"pln",4.70);
    private final Order orderSell = new Order(user,BuyOrSell.SELL,"eur",3000.0,"bitcoin",4.40);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountBalanceService accountBalanceService;
    @Autowired
    private AccountBalanceRepository accountBalanceRepository;
    @Autowired
    private USDollarRepository usDollarRepository;
    @Autowired
    private EuroRepository euroRepository;
    @Autowired
    private SwissFrancRepository swissFrancRepository;
    @Autowired
    private BitcoinRepository bitcoinRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(user);
    }

    @Test
    void saveOrder() {
        //Given
        //When
        Order savedOrder = orderRepository.save(orderBuy);
        //Then
        assertEquals(1,orderRepository.findOrdersByUserId(user.getId()).size());
        //CleanUp
        orderRepository.deleteById(savedOrder.getId());
    }

    @Test
    void updateOrder() throws OrderNotFoundException {
        //Given
        Order savedOrder = orderRepository.save(orderBuy);
        OrderDto orderDto = new OrderDto(BuyOrSell.SELL,"eur",90.0,"chf",4.50);
        //When
        OrderDto updatedOrder = orderService.updateOrder(savedOrder.getId(), orderDto);
        //Then
        assertAll(
                ()->assertEquals(BuyOrSell.SELL,updatedOrder.getBuyOrSell()),
                ()->assertEquals("eur",updatedOrder.getCurFrom()),
                ()->assertEquals(90.0,updatedOrder.getQtyFrom()),
                ()->assertEquals("chf",updatedOrder.getCurTo()),
                ()->assertEquals(4.50,updatedOrder.getExchangeRate())
        );
        //CleanUp
        orderRepository.deleteOrdersByUser(user);
    }

    @Test
    void deleteOrder() {
        //Given
        Order order1 = orderRepository.save(orderBuy);
        orderRepository.save(orderSell);
        //When
        orderService.deleteOrder(order1.getId());
        //Then
        int result = orderRepository.findOrdersByUserId(user.getId()).size();
        assertEquals(1, result);
        //CleanUp
        orderRepository.deleteOrdersByUser(user);
    }

    @Test
    void getOrder() throws OrderNotFoundException {
        //Given
        Order order1 = orderRepository.save(orderBuy);
        //When
        OrderDto orderDto = orderService.getOrder(order1.getId());
        //Then
        assertEquals(order1.getId(),orderDto.getId());
        //CleanUp
        orderRepository.deleteOrdersByUser(user);
    }

    @Test
    void getAllOrdersByUserId() {
        //Given
        orderRepository.save(orderBuy);
        orderRepository.save(orderSell);
        User userB = new User("Mary","Sweet","ms1999","msms","ms1999@gmail.com");
        userRepository.save(userB);
        Order orderB = new Order(userB,BuyOrSell.BUY,"usd",50.0,"pln",4.67);
        orderRepository.save(orderB);
        //When
        List<OrderDto> userOrderList = orderService.getAllOrdersByUserId(user.getId());
        List<OrderDto> userBOrderList = orderService.getAllOrdersByUserId(userB.getId());
        //Then
        assertEquals(2,userOrderList.size());
        assertEquals(1,userBOrderList.size());
        //CleanUp
        orderRepository.deleteOrdersByUser(user);
        orderRepository.deleteOrdersByUser(userB);
        userRepository.delete(userB);
    }

    @Test
    void getCurrentOrdersByUserId() {
        //Given
        Order order1 = new Order(user,BuyOrSell.BUY,"usd",100.0,"pln",4.70, OrderStatus.ORDERED);
        Order order2 = new Order(user,BuyOrSell.SELL,"eur",3000.0,"bitcoin",4.40, OrderStatus.DONE);
        Order order3 = new Order(user,BuyOrSell.BUY,"usd",50.0,"pln",4.67, OrderStatus.CANCELLED);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        //When
        List<OrderDto> currentOrderList = orderService.getCurrentOrdersByUserId(user.getId());
        //Then
        assertEquals(1,currentOrderList.size());
        //CleanUp
        orderRepository.deleteOrdersByUser(user);
    }

    @Test
    void completeOrdersBuyCasePositive() throws CurrencyNotFoundException, UserNotFoundException, AccountBalanceNotFoundException {
        //Given
        USDollar usDollar = new USDollar(4.62,4.67,Currencies.USD);
        usDollarRepository.save(usDollar);
        Euro euro = new Euro(4.45,4.50,Currencies.EUR);
        euroRepository.save(euro);
        Bitcoin bitcoin = new Bitcoin(72000.0,73000.0,Currencies.BTC);
        bitcoinRepository.save(bitcoin);
        AccountBalance accountBalance = new AccountBalance(user,10000.0, 5000.0, 4000.0,3000.0,1.0);
        accountBalanceRepository.save(accountBalance);
        Order order1 = new Order(user,BuyOrSell.BUY,"usd",1000.0,"pln",4.62);
        Order order2 = new Order(user,BuyOrSell.BUY,"eur",1000.0,"bitcoin",4.45);
        Order order3 = new Order(user,BuyOrSell.BUY,"eur",1000.0,"bitcoin",5.90);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        //When
        orderService.completeOrders();
        //Then
        AccountBalance balance = accountBalanceService.getAccountByUser(user.getId());
        assertEquals(6000.0, balance.getUsd());
        assertEquals(5380.0,balance.getPln());
        assertEquals(5000.0,balance.getEur());
        assertTrue(balance.getBtc()>0 && balance.getBtc()<1);
        assertEquals(1, orderService.getCurrentOrdersByUserId(user.getId()).size());
        //CleanUp
        usDollarRepository.delete(usDollar);
        euroRepository.delete(euro);
        bitcoinRepository.delete(bitcoin);
        orderRepository.deleteOrdersByUser(user);
        transactionRepository.deleteByUserId(user.getId());
        accountBalanceRepository.deleteByUserId(user.getId());
    }

    @Test
    void completeOrdersBuyCaseNegative() throws CurrencyNotFoundException {
        //Given
        USDollar usDollar = new USDollar(4.62,4.67,Currencies.USD);
        usDollarRepository.save(usDollar);
        SwissFranc swissFranc = new SwissFranc(4.45,4.50,Currencies.CHF);
        swissFrancRepository.save(swissFranc);
        Bitcoin bitcoin = new Bitcoin(72000.0,73000.0,Currencies.BTC);
        bitcoinRepository.save(bitcoin);
        AccountBalance accountBalance = new AccountBalance(user,10000.0, 5000.0, 4000.0,3000.0,1.0);
        accountBalanceRepository.save(accountBalance);
        Order order1 = new Order(user,BuyOrSell.BUY,"chf",5000.0,"bitcoin",4.10);
        Order order2 = new Order(user,BuyOrSell.BUY,"pln",100000.0,"usd",1);
        Order order3 = new Order(user,BuyOrSell.BUY,"chf",5000.0,"pln",4.45);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        //When
        orderService.completeOrders();
        //Then
        assertEquals(1,orderService.getCurrentOrdersByUserId(user.getId()).size());
        //CleanUp
        usDollarRepository.delete(usDollar);
        swissFrancRepository.delete(swissFranc);
        bitcoinRepository.delete(bitcoin);
        orderRepository.deleteOrdersByUser(user);
        transactionRepository.deleteByUserId(user.getId());
        accountBalanceRepository.deleteByUserId(user.getId());
    }

    @Test
    void completeOrdersSellCasePositive() throws UserNotFoundException, AccountBalanceNotFoundException, CurrencyNotFoundException {
        //Given
        USDollar usDollar = new USDollar(4.62,4.67,Currencies.USD);
        usDollarRepository.save(usDollar);
        SwissFranc swissFranc = new SwissFranc(4.45,4.50,Currencies.CHF);
        swissFrancRepository.save(swissFranc);
        Bitcoin bitcoin = new Bitcoin(72000.0,73000.0,Currencies.BTC);
        bitcoinRepository.save(bitcoin);
        AccountBalance accountBalance = new AccountBalance(user,10000.0, 5000.0, 4000.0,3000.0,1.0);
        accountBalanceRepository.save(accountBalance);
        Order order1 = new Order(user,BuyOrSell.SELL,"usd",1000.0,"pln",4.67);
        Order order2 = new Order(user,BuyOrSell.SELL,"chf",1000.0,"bitcoin",4.50);
        Order order3 = new Order(user,BuyOrSell.SELL,"chf",1000.0,"bitcoin",5.90);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        //When
        orderService.completeOrders();
        //Then
        AccountBalance balance = accountBalanceService.getAccountByUser(user.getId());
        assertEquals(4000.0, balance.getUsd());
        assertEquals(14670.0,balance.getPln());
        assertEquals(2000.0,balance.getChf());
        assertTrue(balance.getBtc()>1 && balance.getBtc()<2);
        //CleanUp
        usDollarRepository.delete(usDollar);
        swissFrancRepository.delete(swissFranc);
        bitcoinRepository.delete(bitcoin);
        orderRepository.deleteOrdersByUser(user);
        transactionRepository.deleteByUserId(user.getId());
        accountBalanceRepository.deleteByUserId(user.getId());
    }

    @Test
    void completeOrdersSellCaseNegative() throws CurrencyNotFoundException {
        //Given
        USDollar usDollar = new USDollar(4.62,4.67,Currencies.USD);
        usDollarRepository.save(usDollar);
        Euro euro = new Euro(4.45,4.50,Currencies.EUR);
        euroRepository.save(euro);
        Bitcoin bitcoin = new Bitcoin(72000.0,73000.0,Currencies.BTC);
        bitcoinRepository.save(bitcoin);
        AccountBalance accountBalance = new AccountBalance(user,10000.0, 5000.0, 4000.0,3000.0,1.0);
        accountBalanceRepository.save(accountBalance);
        Order order1 = new Order(user,BuyOrSell.SELL,"usd",1000.0,"pln",3.90);
        Order order2 = new Order(user,BuyOrSell.SELL,"eur",8000.0,"bitcoin",4.50);
        Order order3 = new Order(user,BuyOrSell.SELL,"bitcoin",2.0,"pln",73000.0);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        //When
        orderService.completeOrders();
        //Then
        assertEquals(1, orderService.getCurrentOrdersByUserId(user.getId()).size());
        //CleanUp
        usDollarRepository.delete(usDollar);
        euroRepository.delete(euro);
        bitcoinRepository.delete(bitcoin);
        orderRepository.deleteOrdersByUser(user);
        transactionRepository.deleteByUserId(user.getId());
        accountBalanceRepository.deleteByUserId(user.getId());
    }
}
