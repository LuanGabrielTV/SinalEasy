package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.dtos.CityDTO;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDTO(City city);
    
    @Mapping(target = "rating", source = "rating")
    CityDTO toDTO(City city, Integer rating);
}

