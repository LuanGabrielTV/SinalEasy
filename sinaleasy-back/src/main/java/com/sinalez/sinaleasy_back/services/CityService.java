package com.sinalez.sinaleasy_back.services;

import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.CityNotFoundException;
import com.sinalez.sinaleasy_back.repositories.CityRepository;

@Service
public class CityService {
    public final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
    }
}
