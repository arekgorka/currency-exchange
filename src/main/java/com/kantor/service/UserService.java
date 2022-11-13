package com.kantor.service;

import com.kantor.domain.User;
import com.kantor.domain.dto.UserDto;
import com.kantor.exception.UserAlreadyExistException;
import com.kantor.exception.UserNotFoundException;
import com.kantor.mapper.UserMapper;
import com.kantor.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public void createUser(final UserDto userDto) throws UserAlreadyExistException {
        User user = userMapper.mapToUser(userDto);
        if (userRepository.count()==0) {
            userRepository.save(user);
        } else if (userRepository.existsByLogin(user.getLogin()) || userRepository.existsByMail(user.getMail())) {
            throw new UserAlreadyExistException();
        } else {
            userRepository.save(user);
        }
    }

    public UserDto getUser(final Long userId) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.mapToUserDto(user);
    }

    public User findUserById(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
