package com.kantor.service;

import com.kantor.domain.AccountBalance;
import com.kantor.domain.User;
import com.kantor.domain.dto.AccountBalanceDto;
import com.kantor.exception.AccountBalanceNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.repository.AccountBalanceRepository;
import com.kantor.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountBalanceServiceTests {
    private final User user = new User("Michael","Jackson","mj2010","Mjmj","mj2010@gmail.com");

    @Autowired
    private AccountBalanceService accountBalanceService;
    @Autowired
    private AccountBalanceRepository accountBalanceRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void saveUserForTests() {
        userRepository.save(user);
    }
    @AfterEach
    public void cleanAccountBalance() {
        accountBalanceRepository.deleteByUserId(user.getId());
        userRepository.delete(user);
    }

    @Test
    void createAccountBalanceTest() throws UserNotFoundException {
        //Given
        //When
        accountBalanceService.createAccount(user.getId());
        //Then
        List<AccountBalance> balanceList = accountBalanceRepository.findAccountBalanceByUserId(user.getId());
        assertEquals(1,balanceList.size());
    }

    @Test
    void saveAccountBalanceTest() throws UserNotFoundException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance = new AccountBalance(user,50000.0,0.0,300.0,10.0,0.5);
        //When
        accountBalanceService.saveAccountBallance(accountBalance);
        //Then
        List<AccountBalance> balanceList = accountBalanceRepository.findAccountBalanceByUserId(user.getId());

        assertEquals(2,balanceList.size());
        assertEquals(50000.0,balanceList.get(1).getPln());
        assertEquals(0.0,balanceList.get(1).getUsd());
        assertEquals(300.0,balanceList.get(1).getEur());
        assertEquals(10.0,balanceList.get(1).getChf());
        assertEquals(0.5,balanceList.get(1).getBtc());
    }

    @Test
    void updateAccountByDeposit() throws UserNotFoundException, AccountBalanceNotFoundException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance = new AccountBalance(user,50000.0,0.0,0.0,0.0,0.0);
        accountBalanceService.saveAccountBallance(accountBalance);
        //When
        AccountBalanceDto balanceDto = accountBalanceService.updateAccountByDeposit(user.getId(), 10000.0);
        //Then
        assertEquals(60000.0,balanceDto.getPln());
    }

    @Test
    void updateAccountByWithdrawal() {
        //Given
        //When
        //Then
    }

    @Test
    void getAccountDtoByUser() {
        //Given
        //When
        //Then
    }

    @Test
    void getAccountByUser() {
        //Given
        //When
        //Then
    }

    @Test
    void getAccountHistory() {
        //Given
        //When
        //Then
    }

    @Test
    void getCurrencyAccountBalance() {
        //Given
        //When
        //Then
    }

}
