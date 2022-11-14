package com.spring.daxa.services.impl;

import com.spring.daxa.dto.UserCreateDto;
import com.spring.daxa.dto.UserDto;
import com.spring.daxa.entity.Role;
import com.spring.daxa.entity.User;
import com.spring.daxa.repositories.RoleRepository;
import com.spring.daxa.repositories.UserRepository;
import com.spring.daxa.security.JwtActions;
import com.spring.daxa.security.context.UserContext;
import com.spring.daxa.security.data.Authorization;
import com.spring.daxa.security.data.Token;
import com.spring.daxa.services.Auth;
import com.spring.daxa.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthImpl implements Auth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtActions jwtActions;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public Token signIn(Authorization authorization) {
        User user = userRepository.findByUsername(authorization.getUsername()).orElseThrow();

        UserContext userContext = new UserContext(
                user.getUsername(),
                user.getPassword(),
                user.getRoles());

        if (passwordEncoder.matches(authorization.getPassword(), user.getPassword())) {
            return new Token(jwtActions.createToken(userContext));
        } else {
            throw new RuntimeException("Аутентификация не пройдена!");
        }
    }


    @Override
    public UserDto registration(UserCreateDto userCreateDto) {
        Role role = roleRepository.getById(2L);
        User user = new User(
                userCreateDto.getUsername(),
                userCreateDto.getName(),
                userCreateDto.getSurname(),
                userCreateDto.getEmail(),
                passwordEncoder.encode(userCreateDto.getPassword())
        );
        user.setRoles(Collections.singleton(role));

        user = userRepository.saveAndFlush(user);

        return userMapper.toDto(user);
    }
}
