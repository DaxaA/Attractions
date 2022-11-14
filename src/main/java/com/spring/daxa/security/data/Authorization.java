package com.spring.daxa.security.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Authorization {
    private final String username;
    private final String password;
    public Authorization(@JsonProperty("username") String username,
                         @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
