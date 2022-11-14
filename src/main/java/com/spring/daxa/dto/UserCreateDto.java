package com.spring.daxa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserCreateDto {

    private final String username;
    private final String name;
    private final String surname;
    private final LocalDate birthdate;
    private final String email;
    private final String password;

    public UserCreateDto(@JsonProperty("username") String username,
                         @JsonProperty("name") String name,
                         @JsonProperty("surname") String surname,
                         @JsonProperty("birthdate") LocalDate birthdate,
                         @JsonProperty("email") String email,
                         @JsonProperty("password") String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }

}
