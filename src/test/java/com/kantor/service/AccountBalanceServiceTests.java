package com.kantor.service;

import com.kantor.domain.AccountBalance;
import com.kantor.domain.User;
import com.kantor.domain.dto.AccountBalanceDto;
import com.kantor.exception.AccountBalanceNotFoundException;
import com.kantor.exception.AccountWithdrawalException;
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

        assertNotNull(balanceList);
        assertAll( "balanceList",
                ()-> assertEquals(2,balanceList.size()),
                ()-> assertEquals(50000.0,balanceList.get(1).getPln()),
                ()-> assertEquals(0.0,balanceList.get(1).getUsd()),
                ()-> assertEquals(300.0,balanceList.get(1).getEur()),
                ()-> assertEquals(10.0,balanceList.get(1).getChf()),
                ()-> assertEquals(0.5,balanceList.get(1).getBtc())
        );

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
    void updateAccountByWithdrawal() throws UserNotFoundException, AccountBalanceNotFoundException, AccountWithdrawalException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance = new AccountBalance(user,50000.0,0.0,0.0,0.0,0.0);
        accountBalanceService.saveAccountBallance(accountBalance);
        //When
        AccountBalanceDto balanceDto = accountBalanceService.updateAccountByWithdrawal(user.getId(), 10000.0);
        //Then
        assertEquals(40000.0,balanceDto.getPln());
    }

    @Test
    void updateAccountByWithdrawalWithTooHighAmount() throws UserNotFoundException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance = new AccountBalance(user,50000.0,0.0,0.0,0.0,0.0);
        accountBalanceService.saveAccountBallance(accountBalance);
        //When & Then
        assertThrows(
                AccountWithdrawalException.class,
                () -> accountBalanceService.updateAccountByWithdrawal(user.getId(), 60000.0),
                "You don't have enough money.");
    }

    @Test
    void getAccountByUser() throws UserNotFoundException, AccountBalanceNotFoundException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance1 = new AccountBalance(user,50000.0,0.0,0.0,0.0,0.0);
        AccountBalance accountBalance2 = new AccountBalance(user,30000.0,5000.0,0.0,0.0,0.0);
        AccountBalance accountBalance3 = new AccountBalance(user,30000.0,3000.0,2000.0,0.0,0.0);
        accountBalanceService.saveAccountBallance(accountBalance1);
        accountBalanceService.saveAccountBallance(accountBalance2);
        accountBalanceService.saveAccountBallance(accountBalance3);
        //When
        AccountBalance accountBalance = accountBalanceService.getAccountByUser(user.getId());
        //Then
        assertNotNull(accountBalance);
        assertAll("accountBalance",
                ()-> assertEquals(30000.0,accountBalance.getPln()),
                ()-> assertEquals(3000.0,accountBalance.getUsd()),
                ()-> assertEquals(2000.0,accountBalance.getEur())
        );
    }

    @Test
    void getAccountHistory() throws UserNotFoundException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance1 = new AccountBalance(user,50000.0,0.0,0.0,0.0,0.0);
        AccountBalance accountBalance2 = new AccountBalance(user,30000.0,5000.0,0.0,0.0,0.0);
        AccountBalance accountBalance3 = new AccountBalance(user,30000.0,3000.0,2000.0,0.0,0.0);
        accountBalanceService.saveAccountBallance(accountBalance1);
        accountBalanceService.saveAccountBallance(accountBalance2);
        accountBalanceService.saveAccountBallance(accountBalance3);
        //When
        List<AccountBalanceDto> balanceDtoList = accountBalanceService.getAccountHistory(user.getId());
        //Then
        assertEquals(4,balanceDtoList.size());
    }

    @Test
    void getCurrencyAccountBalance() throws UserNotFoundException, AccountBalanceNotFoundException {
        //Given
        accountBalanceService.createAccount(user.getId());
        AccountBalance accountBalance = new AccountBalance(user,50000.0,200.0,3.0,4000.0,0.9999);
        accountBalanceService.saveAccountBallance(accountBalance);
        //When
        double usd = accountBalanceService.getCurrencyAccountBalance(user.getId(), "usd");
        double eur = accountBalanceService.getCurrencyAccountBalance(user.getId(), "eur");
        double chf = accountBalanceService.getCurrencyAccountBalance(user.getId(), "chf");
        double btc = accountBalanceService.getCurrencyAccountBalance(user.getId(), "bitcoin");
        double pln = accountBalanceService.getCurrencyAccountBalance(user.getId(), "zloty");
        //Then
        assertAll(
                ()-> assertEquals(200.0,usd),
                ()-> assertEquals(3.0,eur),
                ()-> assertEquals(4000.0,chf),
                ()-> assertEquals(0.9999,btc),
                ()-> assertEquals(50000.0,pln)
        );
    }

}
