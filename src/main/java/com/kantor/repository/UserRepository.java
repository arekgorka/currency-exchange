package com.kantor.repository;

import com.kantor.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    boolean existsByLogin(String userLogin);
    boolean existsByMail(String userMail);

    Optional<User> findById(Long userId);
}
