package com.sinalez.sinaleasy_back.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.sinalez.sinaleasy_back.services.logic.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserAuthenticationController {

    
    // private final UserRepository userRepository;
    private final TokenService tokenService;

    // o authenticationManager eh configurado no SecurityConfiguration
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public UserAuthenticationController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterDTO registerDTO) {
        try {
            userService.registerUser(registerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponseDTO("User registered successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {

        //token a ser retornado pro client
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    // @PostMapping("/register")
    // public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterDTO registerDTO) {
    //     // PASSAR PARA SERVICE

    //     // verifica se o usuario ja existe
    //     if(this.userRepository.findByUserLogin(registerDTO.login()) != null) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponseDTO("Error. Login already exists"));
    //     }

    //     // verifica se o email ja foi cadastrado
    //     if (this.userRepository.existsByUserEmail(registerDTO.email())) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponseDTO("Error. Email already exists"));
    //     }

    //     String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

    //     User newUser = new User(registerDTO.login(), encryptedPassword, registerDTO.role());

    //     this.userRepository.save(newUser);

    //     return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponseDTO("User registered successfully"));
             
    // }


    

}