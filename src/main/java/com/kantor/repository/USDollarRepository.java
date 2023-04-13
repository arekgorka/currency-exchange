package com.kantor.repository;

import com.kantor.domain.currency.USDollar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface USDollarRepository extends CrudRepository<USDollar, Long> {

    List<USDollar> findAll();
}