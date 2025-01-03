package com.sinalez.sinaleasy_back.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.AuthenticationDTO;
import com.sinalez.sinaleasy_back.dtos.RegisterDTO;
import com.sinalez.sinaleasy_back.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth") // possivel erro, deveria ser api/auth?
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    // o metodo vai utilizar o BCrypt para criptografar a senha que chegou por parametro (DTO) e comparar com o hash no banco
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {

        //token a ser retornado pro client
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if(this.userRepository.findByUserLogin(registerDTO.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.login(), encryptedPassword);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
             
    }

}
