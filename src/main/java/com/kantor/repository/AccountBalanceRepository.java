package com.kantor.repository;

import com.kantor.domain.AccountBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AccountBalanceRepository extends CrudRepository<AccountBalance,Long> {

    List<AccountBalance> findAccountBalanceByUserId(Long userId);

    void deleteByUserId(Long userId);
}
