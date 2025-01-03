package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}

