package com.kantor.repository;

import com.kantor.domain.Currency;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface CurrencyRepository extends CrudRepository<Currency,Long> {
}
