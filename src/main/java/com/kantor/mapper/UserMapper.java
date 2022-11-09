package com.kantor.mapper;

import com.kantor.domain.User;
import com.kantor.domain.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getMail());
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getLogin(),
                user.getPassword(),
                user.getMail());
    }
}
