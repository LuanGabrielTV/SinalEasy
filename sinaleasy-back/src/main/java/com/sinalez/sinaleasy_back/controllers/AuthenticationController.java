package com.sinalez.sinaleasy_back.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth") // possivel erro, deveria ser api/auth?
public class AuthenticationController {
    @PostMapping("/login")
    public ResponseEntity login(RequestBody @Valid AuthenticationDTO authenticationDTO)
}