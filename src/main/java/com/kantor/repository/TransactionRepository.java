package com.kantor.repository;

import com.kantor.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findTransactionByUserId(Long userId);
}
