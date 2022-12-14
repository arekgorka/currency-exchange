package com.kantor.controller;

import com.kantor.domain.dto.TransactionDto;
import com.kantor.exception.AccountBallanceNotFoundException;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.exception.WrongValidationException;
import com.kantor.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/currency_exchange/transactions")
@RequiredArgsConstructor
public class TransactionController {

    final private TransactionService transactionService;

    @PostMapping(value = "buy/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBuyTransaction(@PathVariable Long userId, @RequestBody TransactionDto transactionDto)
            throws UserNotFoundException, AccountBallanceNotFoundException, WrongValidationException, CurrencyNotFoundException {
        transactionService.createBuyTransaction(userId, transactionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "sell/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSellTransaction(@PathVariable Long userId, @RequestBody TransactionDto transactionDto)
            throws UserNotFoundException, AccountBallanceNotFoundException, WrongValidationException, CurrencyNotFoundException {
        transactionService.createSellTransaction(userId, transactionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "transactionHistory/user/{userId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByUser(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(transactionService.getTransactionByUser(userId));
    }
}
