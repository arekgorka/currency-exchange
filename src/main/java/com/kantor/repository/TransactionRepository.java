package com.kantor.repository;

import com.kantor.domain.Transaction;
import com.kantor.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findTransactionByUserId(Long userId);

    void deleteByUserId(Long userId);
}
