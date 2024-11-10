package com.sinalez.sinaleasy_back.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sinalez.sinaleasy_back.entities.Signal;

public interface SignalRepository extends JpaRepository<Signal, UUID> {
    Optional<Signal> findByName(String name);
    List<Signal> findByCityId(long cityId);
    
}