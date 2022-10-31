package com.kantor.service;

import com.kantor.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public void createUser(UserDto userDto) {

    }

    public UserDto getUser(final Long userId) {
        return new UserDto();
    }
}
