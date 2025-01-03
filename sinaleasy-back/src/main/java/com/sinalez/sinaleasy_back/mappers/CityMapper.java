package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.dtos.CityDTO;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDTO(City city);

}

