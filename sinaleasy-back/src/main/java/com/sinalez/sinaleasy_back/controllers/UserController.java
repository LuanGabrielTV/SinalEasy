package com.sinalez.sinaleasy_back.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.UserDTO;
import com.sinalez.sinaleasy_back.mappers.UserMapper;
import com.sinalez.sinaleasy_back.services.logic.UserService;

import jakarta.validation.Valid;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/users") // endpoint para criacao de usuarios teste. A criacao real do usuario eh em /api/auth/register/
=======
@RequestMapping("/api/users")
@CrossOrigin

>>>>>>> develop
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userRequestDTO) {
        User createdUser = userService.createUser(userRequestDTO);
        UserDTO userResponseDTO = userMapper.toDTO(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    // curl -X GET http://localhost:8080/api/users/ \ -H "Content-Type: application/json"
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.getUsers();
        List<UserDTO> usersResponseDTO = users.stream()
            .map(userMapper::toDTO)
            .toList();
        return ResponseEntity.status(HttpStatus.OK).body(usersResponseDTO);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID id) {
        User user  = userService.getUserById(id);
        UserDTO userResponseDTO = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    
}
