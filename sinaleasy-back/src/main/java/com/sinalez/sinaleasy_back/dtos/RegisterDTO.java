package com.sinalez.sinaleasy_back.dtos;

import com.sinalez.sinaleasy_back.domains.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank String login, 
        @NotBlank String password, 
        UserRole role, 
        @NotBlank @Email String email
    ) {}
