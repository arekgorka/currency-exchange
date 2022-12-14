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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

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
        assertNotEquals(7000,accountBallanceService.getAccountByUser(userId).getPln());
        assertNotEquals(0,accountBallanceService.getAccountByUser(userId).getUsd());
        //CleanUp
        transactionRepository.deleteByUserId(userId);
        accountBallanceRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
}
