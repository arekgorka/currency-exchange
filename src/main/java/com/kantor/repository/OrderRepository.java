package com.kantor.repository;

import com.kantor.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {

    Optional<Order> findById(Long orderId);

    List<Order> findOrdersByUserId(Long userId);

    List<Order> findAll();
}
