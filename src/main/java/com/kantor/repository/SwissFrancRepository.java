package com.kantor.repository;

import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwissFrancRepository extends CrudRepository<SwissFranc, Long> {

    List<SwissFranc> findAll();
}
