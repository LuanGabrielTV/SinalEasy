package com.sinalez.sinaleasy_back.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.CityRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.CityNotFoundException;
import com.sinalez.sinaleasy_back.repositories.CityRepository;

@Service
public class CityService {
    public final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City createCity(CityRecordDTO cityRecordDTO) {
        City city = new City();
        BeanUtils.copyProperties(cityRecordDTO, city);
        return cityRepository.save(city);
    }

    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
    }
}
