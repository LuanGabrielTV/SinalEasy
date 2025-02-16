package com.sinalez.sinaleasy_back.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.sinalez.sinaleasy_back.domains.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    UserDetails findByUserLogin(String userLogin);
    Optional<User> findUserByUserLogin(String userLogin);
    
    @Query("SELECT u.userId FROM User u WHERE u.userLogin = :userLogin")
    Optional<UUID> findUserIdByUserLogin(@Param("userLogin") String userLogin);

    
}