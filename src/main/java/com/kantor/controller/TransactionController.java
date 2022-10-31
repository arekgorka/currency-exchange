package com.kantor.controller;

import com.kantor.domain.dto.TransactionDto;
import com.kantor.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/currency_exchange/transactions")
@RequiredArgsConstructor
public class TransactionController {

    final private TransactionService transactionService;

    @PostMapping(value = "buy/{userId}")
    public ResponseEntity<Void> createBuyTransaction(@PathVariable Long userId) {
        transactionService.createBuyTransaction(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "sell/{userId}")
    public ResponseEntity<Void> createSellTransaction(@PathVariable Long userId) {
        transactionService.createSellTransaction(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "transactionHistory/user/{userId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionByUser(userId));
    }
}
