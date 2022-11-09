package com.kantor.repository;

import com.kantor.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    boolean existsByLogin(String userLogin);
    boolean existsByMail(String userMail);
}
