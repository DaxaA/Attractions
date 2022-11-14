package com.spring.daxa.security;

import com.spring.daxa.entity.Role;
import com.spring.daxa.security.context.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtActions {
    private final String secret;

    private final Integer timeLife;

    public JwtActions(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.time-life}") Integer timeLife) {
        this.secret = secret;
        this.timeLife = timeLife;
    }

    public String createToken(UserContext userContext) {
        LocalDateTime createToken = LocalDateTime.now();
        Date dateTimeLife = Date.from(createToken.plusMinutes(timeLife)
                .atZone(ZoneId.systemDefault()).toInstant());

        List<String> roles = userContext.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userContext.getEmail())
                .claim("roles", roles)
                .setNotBefore(Date.from(createToken.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(dateTimeLife)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ResourceAccessException e) {
            throw new ResourceAccessException("invalid token");
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
