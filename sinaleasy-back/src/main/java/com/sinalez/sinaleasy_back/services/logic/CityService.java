package com.sinalez.sinaleasy_back.services.logic;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.domains.City;
import com.sinalez.sinaleasy_back.domains.SignalStatus;
import com.sinalez.sinaleasy_back.dtos.CityDTO;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.cityExceptions.CityIdNotFoundException;
import com.sinalez.sinaleasy_back.mappers.CityMapper;
import com.sinalez.sinaleasy_back.repositories.CityRepository;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
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
    }

    public void calculateCityRating(City city, CityDTO cityDTO) {
        double avgRatingFinished = 0;
        double avgDelayUnfinished = 0;
        int countFinished = 0;
        LocalDate now = LocalDate.now();
        for (int i = 0; i < city.getSignals().size(); i++) {
            if(city.getSignals().get(i).getStatus() == SignalStatus.FINISHED.getCode()) {
                avgRatingFinished += city
                    .getSignals()
                    .get(i)
                    .getGrade()
                    .getRating();
                countFinished++;
            } else {
                avgDelayUnfinished += ChronoUnit.DAYS.between(city.getSignals().get(i).getDate(), now);
            }
        }
        avgRatingFinished /= (countFinished + 1);
        avgDelayUnfinished /= ((city.getSignals().size() - countFinished) + 1);
        Integer rating = Integer.valueOf((int) ((avgRatingFinished / (1 + avgDelayUnfinished))));
        cityDTO = cityMapper.toDTO(city, rating);
    }
    

}
