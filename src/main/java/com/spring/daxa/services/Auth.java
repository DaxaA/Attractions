package com.spring.daxa.services;


import com.spring.daxa.dto.UserCreateDto;
import com.spring.daxa.dto.UserDto;
import com.spring.daxa.security.data.Authorization;
import com.spring.daxa.security.data.Token;

public interface Auth {
    Token signIn(Authorization authorization);
    UserDto registration(UserCreateDto userCreateDto);
}
