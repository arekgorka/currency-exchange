package com.kantor.repository;

import com.kantor.domain.AccountBallance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountBallanceRepository extends CrudRepository<AccountBallance,Long> {

    List<AccountBallance> findAccountBallanceByUserId(Long userId);
}
