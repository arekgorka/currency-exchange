package com.kantor.repository;

import com.kantor.domain.AccountBallance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountBallance,Long> {
}
