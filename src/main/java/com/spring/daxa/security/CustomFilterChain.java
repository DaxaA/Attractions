package com.spring.daxa.security;

import com.spring.daxa.cookie.CookieToken;
import com.spring.daxa.security.context.AuthContextImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomFilterChain extends GenericFilterBean {

    private final JwtActions jwtActions;
    private final CookieToken cookieToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setSecurityContext(cookieToken.getTokenFromCookie(request));
        chain.doFilter(request, response);
    }

    private void setSecurityContext(String token) {
        if (token != null && jwtActions.validate(token)) {
            Claims claims = jwtActions.getClaims(token);
            AuthContextImpl authContext = JwtUtils.generate(claims);
            authContext.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(authContext);
        }
    }
}
