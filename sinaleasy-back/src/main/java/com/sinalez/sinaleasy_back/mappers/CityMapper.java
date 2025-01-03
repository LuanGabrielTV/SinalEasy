package com.sinalez.sinaleasy_back.mappers;

import org.mapstruct.Mapper;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.dtos.CityRecordDTO;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityRecordDTO toDTO(City city);

}

