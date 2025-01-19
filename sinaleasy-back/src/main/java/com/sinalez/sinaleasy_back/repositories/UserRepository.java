package com.sinalez.sinaleasy_back.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinalez.sinaleasy_back.domains.Signal;
import com.sinalez.sinaleasy_back.domains.User;

public interface UserRepository extends JpaRepository<User, UUID>{

    
}