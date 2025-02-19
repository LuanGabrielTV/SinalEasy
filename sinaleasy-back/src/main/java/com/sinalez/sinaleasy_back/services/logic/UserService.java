package com.sinalez.sinaleasy_back.services.logic;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.AuthenticationDTO;
import com.sinalez.sinaleasy_back.dtos.RegisterDTO;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions.UserAlreadyExists;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions.UserEmailIsBlank;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions.UserNotFoundException;
import com.sinalez.sinaleasy_back.infra.security.TokenService;
import com.sinalez.sinaleasy_back.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public User registerUser(RegisterDTO registerDTO) {
        if(userRepository.findByUserLogin(registerDTO.login()) != null)
            throw new UserAlreadyExists("Este username já está em uso por outro usuário");
        if(registerDTO.email() == null)
            throw new UserEmailIsBlank();
        if(userRepository.existsByUserEmail(registerDTO.email()))
            throw new UserAlreadyExists("Este email já está em uso por outro usuário");
        
        String encryptedPassword = new BCryptPasswordEncoder()
                .encode(registerDTO.password());
        User newUser = new User(
                registerDTO.login(), 
                encryptedPassword, 
                registerDTO.role());
        return userRepository.save(newUser);
    }

    public String loginUser(AuthenticationDTO authenticationDTO) {
        //token a ser retornado pro client
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        // o authenticationManager eh configurado no SecurityConfiguration
        var auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public UUID getUserIdByUserLogin(String userLogin) {
        return userRepository.findUserIdByUserLogin(userLogin)
                .orElseThrow(UserNotFoundException::new);
    }


}

