package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.dtos.UserRecordDTO;
import com.sinalez.sinaleasy_back.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRecordDTO toDTO(User user);
}

