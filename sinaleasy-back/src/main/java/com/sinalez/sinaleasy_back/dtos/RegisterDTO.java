package com.sinalez.sinaleasy_back.dtos;

import com.sinalez.sinaleasy_back.domains.UserRole;

public record RegisterDTO(String login, String password, UserRole role, String email) {
    
}
