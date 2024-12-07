package com.sinalez.sinaleasy_back.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinalez.sinaleasy_back.dtos.UserRecordDTO;
import com.sinalez.sinaleasy_back.entities.User;
import com.sinalez.sinaleasy_back.mappers.UserMapper;
import com.sinalez.sinaleasy_back.services.logic.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/")
    public ResponseEntity<UserRecordDTO> createUser(@RequestBody @Valid UserRecordDTO userRecordDTO) {
        User createdUser = userService.createUser(userRecordDTO);
        UserRecordDTO userResponseDTO = userMapper.toDTO(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID id) {
        User user  = userService.getUserById(id);
        UserRecordDTO userResponseDTO = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
    
}
