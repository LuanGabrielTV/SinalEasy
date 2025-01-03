package com.sinalez.sinaleasy_back.controllers;

import org.springframework.http.HttpStatus;
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
import com.sinalez.sinaleasy_back.dtos.LoginResponseDTO;
import com.sinalez.sinaleasy_back.dtos.RegisterDTO;
import com.sinalez.sinaleasy_back.dtos.RegisterResponseDTO;
import com.sinalez.sinaleasy_back.infra.security.TokenService;
import com.sinalez.sinaleasy_back.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    // o metodo vai utilizar o BCrypt para criptografar a senha que chegou por parametro (DTO) e comparar com o hash no banco
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {

        //token a ser retornado pro client
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterDTO registerDTO) {
        // no momento, o unico caso que impede o registro eh se ele ja existir
        if(this.userRepository.findByUserLogin(registerDTO.login()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponseDTO("Error. Login already exists"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.login(), encryptedPassword);

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponseDTO("User registered successfully"));
             
    }

}
