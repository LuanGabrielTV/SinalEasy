// package com.sinalez.sinaleasy_back.services.security;

// import org.springframework.stereotype.Service;


// @Service
// public class TokenService {
//     @Value

//     @Value("${api.security.token.secret}")
//     private String secret;

//     public String generateToken(User user) {
//         try {
//             Algorithm algorithm = Algorithm.HMAC256(secret);
//             String token = JWT.create()
//                 .withIssuer("auth-api")
//                 .withSubject(user.getLogin())
//                 .withExpiresAt(genExpirationDate())
//                 .sign(algorithm);
//         } catch (Exception e) {
//             // TODO: handle exception
//         }
//     }
// }


// package br.com.jonascamargo.travelsmanager.services.security;

// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.exceptions.JWTCreationException;
// import com.auth0.jwt.exceptions.JWTVerificationException;

// import br.com.jonascamargo.travelsmanager.exceptions.customExceptions.TokenGenerationException;
// import br.com.jonascamargo.travelsmanager.models.User;

// @Service
// public class TokenService {

//     // env variable in  application properties
//     @Value("${api.security.token.secret}")
//     private String secret;

//     public String generateToken(User user) {
//         try {
//             // HMAC256 algorithm receives a secret as a parameter, which is an environment variable
//             Algorithm algorithm = Algorithm.HMAC256(secret);
//             String token = JWT.create()
//                             .withIssuer("auth-api")
//                             .withSubject(user.getLogin())
//                             .withExpiresAt(genExpirationDate())
//                             .sign(algorithm);
//             return token;
//         } catch (JWTCreationException exception) {
//             throw new TokenGenerationException();
//         }
//     }

//     public String validateToken(String token){
//         try {
//             Algorithm algorithm = Algorithm.HMAC256(secret);
//             return JWT.require(algorithm)
//                     .withIssuer("auth-api")
//                     .build()
//                     .verify(token)
//                     .getSubject();
//         } catch (JWTVerificationException exception){
//             // Empty string, as the method to be used will already identify that it is invalid
//             return "";
//         }
//     }

//     private Instant genExpirationDate() {
//         return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
//     }
// }
