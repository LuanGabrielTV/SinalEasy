package com.sinalez.sinaleasy_back.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserLogin(username);
    }
}


// Toda vez que alguem tentar autenticar na nossa aplicação, o spring security vai precisar de uma forma de identificar esses usuários. Ele não sabe de onde vem esses usuários (podem vir de APIs de terceiros como o google por exemplo). A classe é uma abstração para o Spring