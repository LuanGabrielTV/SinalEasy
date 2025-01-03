package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.UserRecordDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRecordDTO toDTO(User user);
}

