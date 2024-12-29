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


