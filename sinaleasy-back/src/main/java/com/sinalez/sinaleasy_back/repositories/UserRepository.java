package com.sinalez.sinaleasy_back.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// import org.springframework.security.core.userdetails.UserDetails;

import com.sinalez.sinaleasy_back.entities.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    // UserDetails findByLogin(String login);
    
}