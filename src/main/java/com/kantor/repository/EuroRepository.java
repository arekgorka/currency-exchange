package com.kantor.repository;

import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.USDollar;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EuroRepository extends CrudRepository<Euro, Long> {

    List<Euro> findAll();

}
