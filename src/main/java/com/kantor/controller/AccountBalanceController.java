package com.kantor.controller;

import com.kantor.domain.dto.AccountBalanceDto;
import com.kantor.exception.AccountBalanceNotFoundException;
import com.kantor.exception.AccountWithdrawalException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.service.AccountBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/currency_exchange/accounts")
@RequiredArgsConstructor
public class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;

    @PostMapping(value = "user/{userId}")
    public ResponseEntity<Void> createAccount(@PathVariable Long userId) throws UserNotFoundException {
        accountBalanceService.createAccount(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "deposite/{userId}/{amount}")
    public ResponseEntity<AccountBalanceDto> updateAccountByDeposit(@PathVariable Long userId, @PathVariable double amount)
            throws UserNotFoundException, AccountBalanceNotFoundException {
        return ResponseEntity.ok(accountBalanceService.updateAccountByDeposit(userId, amount));
    }

    @PostMapping(value = "withdrawal/{userId}/{amount}")
    public ResponseEntity<AccountBalanceDto> updateAccountByWithdrawal(@PathVariable Long userId, @PathVariable double amount)
            throws UserNotFoundException, AccountBalanceNotFoundException, AccountWithdrawalException {
        return ResponseEntity.ok(accountBalanceService.updateAccountByWithdrawal(userId, amount));
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<AccountBalanceDto> getAccountBalance(@PathVariable Long userId)
            throws UserNotFoundException, AccountBalanceNotFoundException {
        return ResponseEntity.ok(accountBalanceService.getAccountDtoByUser(userId));
    }

    @GetMapping(value = "history/{userId}")
    public ResponseEntity<List<AccountBalanceDto>> getAccountHistory(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(accountBalanceService.getAccountHistory(userId));
    }
}
