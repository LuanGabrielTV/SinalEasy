package com.sinalez.sinaleasy_back.mappers;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sinalez.sinaleasy_back.dtos.CityRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityRecordDTO toDTO(City city);
    
    @Mapping(target = "rating", source = "rating")
    CityRecordDTO toDTO(City city, Integer rating);
}

