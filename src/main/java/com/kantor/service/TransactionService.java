package com.kantor.service;

import com.kantor.domain.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    public void createBuyTransaction(final Long userId) {

    }

    public void createSellTransaction(final Long userId) {

    }

    public List<TransactionDto> getTransactionByUser(final Long userId) {
        return new ArrayList<>();
    }
}
