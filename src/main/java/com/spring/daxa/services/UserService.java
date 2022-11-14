package com.spring.daxa.services;

import com.spring.daxa.dto.UserCreateDto;
import com.spring.daxa.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto userCreateDto);
    List<UserDto> getAllUser();
}
