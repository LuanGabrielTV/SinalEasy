package com.sinalez.sinaleasy_back.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinalez.sinaleasy_back.dtos.CityRecordDTO;
import com.sinalez.sinaleasy_back.entities.City;
import com.sinalez.sinaleasy_back.mappers.CityMapper;
import com.sinalez.sinaleasy_back.services.CityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cities")
// @CrossOrigin
@CrossOrigin(origins = "http://localhost:4200")
public class CityController {
    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    // @PostMapping("/")
    // public ResponseEntity<City> createCity(@RequestBody @Valid CityRecordDTO cityRecordDTO) {
    //     City createdCity = cityService.createCity(cityRecordDTO);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    // }

    @PostMapping("/")
    public ResponseEntity<CityRecordDTO> createCity(@RequestBody @Valid CityRecordDTO cityRecordDTO) {
        City createdCity = cityService.createCity(cityRecordDTO);
        CityRecordDTO cityResponseDTO = cityMapper.toDTO(createdCity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCityById(@PathVariable(value = "id") String id) {
        City city = cityService.getCityById(id);
        CityRecordDTO cityResponseDTO = cityMapper.toDTO(city);
        return ResponseEntity.status(HttpStatus.OK).body(cityResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getCities() {
        List<City> cities = cityService.getCities();
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }


}
