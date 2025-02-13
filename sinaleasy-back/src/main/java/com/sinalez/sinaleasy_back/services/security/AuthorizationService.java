package com.sinalez.sinaleasy_back.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sinalez.sinaleasy_back.repositories.UserRepository;

@Component
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;

    // public AuthorizationService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserLogin(username);
    }
}
