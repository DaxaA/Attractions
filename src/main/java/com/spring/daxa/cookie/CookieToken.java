package com.spring.daxa.cookie;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class CookieToken {
    public void createCookieToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("CookieToken", token);
        cookie.setPath("/");
        cookie.setMaxAge(1800);

        response.addCookie(cookie);
        response.setContentType("/cookie/token");
    }

    public String getTokenFromCookie(ServletRequest request) {
        if (((HttpServletRequest) request).getCookies() == null) {
            return null;
        }
        if (Arrays.stream(((HttpServletRequest) request)
                        .getCookies()).filter(cookie -> cookie.getName()
                        .equals("CookieToken"))
                .map(Cookie::getValue).toList().isEmpty()) {
            return null;
        }
        return Arrays.stream(((HttpServletRequest) request)
                        .getCookies()).filter(cookie -> cookie.getName()
                        .equals("CookieToken")).map(Cookie::getValue)
                .findFirst().orElseThrow();
    }
}
