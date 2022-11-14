package com.spring.daxa.services.mapper;

import com.spring.daxa.dto.UserDto;
import com.spring.daxa.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> toDtoList(List<User> users);
}
