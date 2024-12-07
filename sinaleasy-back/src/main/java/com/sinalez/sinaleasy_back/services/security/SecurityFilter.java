// package com.sinalez.sinaleasy_back.services.security;

// import com.sinalez.sinaleasy_back.repositories.UserRepository;


// @Component
// public class SecurityFilter extends OncePerRequestFilter {
//     private TokenService tokenService;
//     private  UserRepository userRepository;

//     public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
//         this.tokenService = tokenService,
//         this.userRepository = userRepository
//     }
// }


// package br.com.jonascamargo.travelsmanager.services.security;

// import java.io.IOException;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import br.com.jonascamargo.travelsmanager.repositories.UserRepository;
// import br.com.jonascamargo.travelsmanager.services.security.TokenService;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class SecurityFilter extends OncePerRequestFilter {

//     private TokenService tokenService;
//     private UserRepository userRepository;

//     public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
//         this.tokenService = tokenService;
//         this.userRepository = userRepository;
//     }
// }