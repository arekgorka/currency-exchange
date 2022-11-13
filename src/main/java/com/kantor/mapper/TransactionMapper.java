package com.kantor.mapper;

import com.kantor.domain.Transaction;
import com.kantor.domain.dto.TransactionDto;
import com.kantor.exception.UserNotFoundException;
import com.kantor.repository.UserRepository;
import com.kantor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final UserService userService;

    public Transaction mapToTransaction(Long userId, TransactionDto transactionDto) throws UserNotFoundException {
        return new Transaction(
                userService.findUserById(userId),
                transactionDto.getBuyOrSell(),
                transactionDto.getCurFrom(),
                transactionDto.getQtyFrom(),
                transactionDto.getCurTo()
        );
    }

    public TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getDatetime(),
                transaction.getBuyOrSell(),
                transaction.getCurFrom(),
                transaction.getQtyFrom(),
                transaction.getCurTo(),
                transaction.getSum(),
                transaction.getExchangeRate()
        );
    }

    public List<TransactionDto> mapToListTransactionDto(List<Transaction> transactionList) {
        return transactionList.stream()
                .map(this::mapToTransactionDto)
                .collect(Collectors.toList());
    }
}
