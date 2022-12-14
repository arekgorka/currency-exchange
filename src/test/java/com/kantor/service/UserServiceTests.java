package com.kantor.service;

import com.kantor.domain.User;
import com.kantor.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUserTest() {
        //given
        User user = new User("John", "Smith", "blabla", "02022", "user@domain.com");

        System.out.println("user:" + user);
        //when
        User savedUser = userRepository.save(user);
        System.out.println("user:" + savedUser);

        //then
        assertNotEquals(0, userRepository.findById(savedUser.getId()).get().getId());

        assertEquals(user.getLogin(), userRepository.findById(savedUser.getId()).get().getLogin());
        assertEquals(user.getMail(), userRepository.findById(savedUser.getId()).get().getMail());

        //cleanup
        userRepository.delete(savedUser);
    }
}
