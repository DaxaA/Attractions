package com.spring.daxa.services.mapper;

import com.spring.daxa.dto.UserDto;
import com.spring.daxa.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User model);
}
