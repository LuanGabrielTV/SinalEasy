package com.sinalez.sinaleasy_back.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinalez.sinaleasy_back.domains.City;


@Repository
public interface CityRepository extends JpaRepository<City, String> {
    Optional<City> findByName(String name);

}