package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.dtos.CityRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityRecordDTO toDTO(City city);

    
}

