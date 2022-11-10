package com.kantor.repository;

import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.currency.Euro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitcoinRepository extends CrudRepository<Bitcoin, Long> {

    List<Bitcoin> findAll();
}
