package com.kantor.controller;

import com.kantor.domain.dto.AccountDto;
import com.kantor.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/currency_exchange/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "user/{userId}")
    public ResponseEntity<AccountDto> createAccount(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.createAccount(userId));
    }

    @PutMapping(value = "deposite/{userId}")
    public ResponseEntity<AccountDto> updateAccountByDeposit(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.updateAccountByDeposit(userId));
    }

    @PutMapping(value = "withdrawal/{userId}")
    public ResponseEntity<AccountDto> updateAccountByWithdrawal(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.updateAccountByWithdrawal(userId));
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<AccountDto> getAccountBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountByUser(userId));
    }

}
