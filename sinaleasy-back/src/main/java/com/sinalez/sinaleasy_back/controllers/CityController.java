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
import com.sinalez.sinaleasy_back.services.CityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins = "http://localhost:4200")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/")
    public ResponseEntity<City> createCity(@RequestBody @Valid CityRecordDTO cityRecordDTO) {
        City createdCity = cityService.createCity(cityRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCityById(@PathVariable(value = "id") String id) {
        City city = cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(city);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getCities() {
        List<City> cities = cityService.getCities();
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }



}
