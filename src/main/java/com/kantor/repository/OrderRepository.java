package com.kantor.repository;

import com.kantor.domain.Order;
import com.kantor.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {

    Optional<Order> findById(Long orderId);

    List<Order> findOrdersByUserId(Long userId);

    List<Order> findAll();

    @Modifying
    @Query(value = "update ORDERS set orderStatus= :orderStatus where id= :orderId")
    void updateOrderStatus(Long orderId, String orderStatus);

    void deleteOrdersByUser(User user);
}
