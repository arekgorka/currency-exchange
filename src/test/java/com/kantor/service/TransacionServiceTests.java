package com.kantor.service;

import com.kantor.domain.*;
import com.kantor.domain.dto.TransactionDto;
import com.kantor.exception.AccountBallanceNotFoundException;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.exception.WrongValidationException;
import com.kantor.repository.AccountBallanceRepository;
import com.kantor.repository.TransactionRepository;
import com.kantor.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransacionServiceTests {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountBallanceRepository accountBallanceRepository;
    @Autowired
    private AccountBallanceService accountBallanceService;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void deleteAll() {
        transactionRepository.deleteAll();
        accountBallanceRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void updateCurBallanceTest() {
        //Given
        User user = new User();
        AccountBallance accountBallance = new AccountBallance(user,0,0,0,0,0);
        //When
        transactionService.updateCurBallance(Currencies.USD,150.8,accountBallance);
        //Then
        Assertions.assertEquals(150.8,accountBallance.getUsd());
    }

    @Test
    void saveTransactionTest() {
        //Given
        User user = new User("John","Snow","JSnow","qwerty","johnsnow@gmail.com");
        User savedUser = userRepository.save(user);
        Long userId = userRepository.findUserById(savedUser.getId()).get().getId();
        Transaction transaction = new Transaction(user, LocalDateTime.now(),BuyOrSell.BUY,Currencies.USD,500,Currencies.PLN,2400,4.8);
        //When
        Transaction savedTransaction = transactionRepository.save(transaction);
        //Then
        assertTrue(transactionRepository.existsById(savedTransaction.getId()));
        //CleanUp
        transactionRepository.deleteById(savedTransaction.getId());
        userRepository.deleteById(userId);
    }

    @Test
    void createBuyTransactionTest() throws UserNotFoundException, AccountBallanceNotFoundException, CurrencyNotFoundException {
        //Given
        User user = new User("John","Snow","JSnow","qwerty","johnsnow@gmail.com");
        User savedUser = userRepository.save(user);
        Long userId = userRepository.findUserById(savedUser.getId()).get().getId();
        AccountBallance accountBallance = new AccountBallance(1L,user,10000,0,0,0,0);
        accountBallanceRepository.save(accountBallance);
        TransactionDto transactionDto = new TransactionDto(BuyOrSell.BUY,Currencies.USD,500,Currencies.PLN);
        //When
        try {
            transactionService.createBuyTransaction(userId, transactionDto);
        } catch (WrongValidationException e) {
            System.out.println(e);
        }

        //Then
        assertEquals(1,transactionRepository.findTransactionByUserId(userId).size());
        assertNotEquals(10000,accountBallanceService.getAccountByUser(userId).getPln());
        assertNotEquals(0,accountBallanceService.getAccountByUser(userId).getUsd());
        //CleanUp
        transactionRepository.deleteByUserId(userId);
        accountBallanceRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    @Test
    void createSellTransactionTest() throws UserNotFoundException, AccountBallanceNotFoundException, CurrencyNotFoundException {
        //Given
        User user = new User("John","Snow","JSnow","qwerty","johnsnow@gmail.com");
        User savedUser = userRepository.save(user);
        AccountBallance accountBallance = new AccountBallance(1L,user,10000,0,0,0,0);
        accountBallanceRepository.save(accountBallance);
        TransactionDto transactionDto = new TransactionDto(BuyOrSell.SELL,Currencies.PLN,3000,Currencies.USD);
        //When
        try {
            transactionService.createSellTransaction(savedUser.getId(), transactionDto);
        } catch (WrongValidationException e) {
            System.out.println(e);
        }
        //Then
        assertEquals(1, transactionRepository.findTransactionByUserId(savedUser.getId()).size());
        assertTrue(accountBallanceService.getAccountByUser(savedUser.getId()).getPln() < 10000);
        assertTrue(accountBallanceService.getAccountByUser(savedUser.getId()).getUsd() > 0);
        //CleanUp
        transactionRepository.deleteByUserId(savedUser.getId());
        accountBallanceRepository.deleteByUserId(savedUser.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void getTransactionByUserTest() throws UserNotFoundException, AccountBallanceNotFoundException, CurrencyNotFoundException, WrongValidationException {
        //Given
        User user = new User("John","Snow","JSnow","qwerty","johnsnow@gmail.com");
        User savedUser = userRepository.save(user);
        AccountBallance accountBallance = new AccountBallance(1L,user,10000,0,0,0,0);
        accountBallanceRepository.save(accountBallance);
        TransactionDto transactionDto1 = new TransactionDto(BuyOrSell.BUY,Currencies.USD,500,Currencies.PLN);
        TransactionDto transactionDto2 = new TransactionDto(BuyOrSell.SELL,Currencies.PLN,3000,Currencies.USD);
        TransactionDto transactionDto3 = new TransactionDto(BuyOrSell.BUY,Currencies.EUR,100,Currencies.USD);
        transactionService.createBuyTransaction(savedUser.getId(), transactionDto1);
        transactionService.createSellTransaction(savedUser.getId(), transactionDto2);
        transactionService.createBuyTransaction(savedUser.getId(), transactionDto3);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto1);
        transactionDtoList.add(transactionDto2);
        transactionDtoList.add(transactionDto3);
        //When
        List<TransactionDto> userTransactions = transactionService.getTransactionByUser(savedUser.getId());
        //Then
        assertEquals(transactionDtoList.size(),userTransactions.size());
        assertEquals(transactionDtoList.get(0).getQtyFrom(),userTransactions.get(0).getQtyFrom());
        assertEquals(transactionDtoList.get(1).getQtyFrom(),userTransactions.get(1).getQtyFrom());
        assertEquals(transactionDtoList.get(2).getQtyFrom(),userTransactions.get(2).getQtyFrom());
        //CleanUp
        transactionRepository.deleteByUserId(savedUser.getId());
        accountBallanceRepository.deleteByUserId(savedUser.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void accountBallanceValidationForBuyTest() throws UserNotFoundException, AccountBallanceNotFoundException {
        //Given
        User user = new User("John","Snow","JSnow","qwerty","johnsnow@gmail.com");
        User savedUser = userRepository.save(user);
        AccountBallance accountBallance = new AccountBallance(1L,user,0,500,0,0,0);
        accountBallanceRepository.save(accountBallance);
        //When
        boolean resultTrue = transactionService.accountBallanceValidationForBuy(savedUser.getId(),490, Currencies.USD);
        boolean resultFalse = transactionService.accountBallanceValidationForBuy(savedUser.getId(),510, Currencies.USD);
        //Then
        assertTrue(resultTrue);
        assertFalse(resultFalse);
        //CleanUp
        accountBallanceRepository.deleteByUserId(savedUser.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void accountBallanceValidationForSellTest() throws UserNotFoundException, AccountBallanceNotFoundException {
        //Given
        User user = new User("John","Snow","JSnow","qwerty","johnsnow@gmail.com");
        User savedUser = userRepository.save(user);
        AccountBallance accountBallance = new AccountBallance(1L,user,0,500,0,0,0);
        accountBallanceRepository.save(accountBallance);
        //When
        boolean resultTrue = transactionService.accountBallanceValidationForSell(savedUser.getId(),490, Currencies.USD);
        boolean resultFalse = transactionService.accountBallanceValidationForSell(savedUser.getId(),510, Currencies.USD);
        //Then
        assertTrue(resultTrue);
        assertFalse(resultFalse);
        //CleanUp
        accountBallanceRepository.deleteByUserId(savedUser.getId());
        userRepository.deleteById(savedUser.getId());
    }
}
