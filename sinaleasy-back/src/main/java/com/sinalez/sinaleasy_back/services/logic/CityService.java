package com.sinalez.sinaleasy_back.services.logic;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.dtos.CityDTO;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.cityExceptions.CityIdNotFoundException;
import com.sinalez.sinaleasy_back.repositories.CityRepository;

@Service
public class CityService {
    public final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City createCity(CityDTO cityRequestDTO) {
        if(cityRequestDTO.cityId() == null) {
            throw new CityIdNotFoundException();
        }
        City city = new City();
        BeanUtils.copyProperties(cityRequestDTO, city);
        return cityRepository.save(city);
    }

    public List<City> getCities(){
        return cityRepository.findAll();
    }

    public City getCityById(String id) {
        return cityRepository.findById(id).orElse(null);
       //  return cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
    }

    

}
