package com.spring.daxa.services.impl;

import com.spring.daxa.dto.UserCreateDto;
import com.spring.daxa.dto.UserDto;
import com.spring.daxa.entity.User;
import com.spring.daxa.repositories.UserRepository;
import com.spring.daxa.services.UserService;
import com.spring.daxa.services.mapper.UserListMapper;
import com.spring.daxa.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        User user = new User(
                userCreateDto.getEmail(),
                userCreateDto.getPassword()
        );

        user = userRepository.saveAndFlush(user);

        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return userListMapper.toDtoList(users);
    }
}
