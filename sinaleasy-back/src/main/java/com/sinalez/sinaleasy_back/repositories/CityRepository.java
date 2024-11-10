package com.sinalez.sinaleasy_back.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinalez.sinaleasy_back.entities.City;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);
}