package com.sinalez.sinaleasy_back.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinalez.sinaleasy_back.entities.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, UUID>{
    
}