package com.spring.daxa.controllers;

import com.spring.daxa.cookie.CookieToken;
import com.spring.daxa.dto.UserCreateDto;
import com.spring.daxa.dto.UserDto;
import com.spring.daxa.security.data.Authorization;
import com.spring.daxa.security.data.Token;
import com.spring.daxa.services.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final Auth auth;
    private final CookieToken cookieToken;

    @PostMapping("/registration")
    public UserDto createUser(UserCreateDto userCreateDto) {
        return auth.registration(userCreateDto);
    }

    @PostMapping("/auth")
    public String signIn(HttpServletResponse response, Authorization authorization) {
        Token token = auth.signIn(authorization);
        cookieToken.createCookieToken(response, token.getToken());

        return "Вы успешно вошли в систему!";
    }
}
