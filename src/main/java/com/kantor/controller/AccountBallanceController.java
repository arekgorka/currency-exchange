package com.kantor.controller;

import com.kantor.domain.dto.AccountBallanceDto;
import com.kantor.exception.AccountBallanceNotFoundException;
import com.kantor.exception.AccountWithdrawalException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.service.AccountBallanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/currency_exchange/accounts")
@RequiredArgsConstructor
public class AccountBallanceController {

    private final AccountBallanceService accountBallanceService;

    @PostMapping(value = "user/{userId}")
    public ResponseEntity<Void> createAccount(@PathVariable Long userId) throws UserNotFoundException {
        accountBallanceService.createAccount(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "deposite/{userId}/{amount}")
    public ResponseEntity<AccountBallanceDto> updateAccountByDeposit(@PathVariable Long userId, @PathVariable double amount)
            throws UserNotFoundException, AccountBallanceNotFoundException {
        return ResponseEntity.ok(accountBallanceService.updateAccountByDeposit(userId, amount));
    }

    @PostMapping(value = "withdrawal/{userId}/{amount}")
    public ResponseEntity<AccountBallanceDto> updateAccountByWithdrawal(@PathVariable Long userId, @PathVariable double amount)
            throws UserNotFoundException, AccountBallanceNotFoundException, AccountWithdrawalException {
        return ResponseEntity.ok(accountBallanceService.updateAccountByWithdrawal(userId, amount));
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<AccountBallanceDto> getAccountBalance(@PathVariable Long userId)
            throws UserNotFoundException, AccountBallanceNotFoundException {
        return ResponseEntity.ok(accountBallanceService.getAccountDtoByUser(userId));
    }

    @GetMapping(value = "history/{userId}")
    public ResponseEntity<List<AccountBallanceDto>> getAccountHistory(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(accountBallanceService.getAccountHistory(userId));
    }
}
