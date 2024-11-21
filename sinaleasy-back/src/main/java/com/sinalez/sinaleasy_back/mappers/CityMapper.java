package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sinalez.sinaleasy_back.dtos.CityRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;

@Mapper(componentModel = "spring")
public interface CityMapper {
    // @Mapping(target = "cityId", ignore = true)
    CityRecordDTO toDTO(City city);

    
}

