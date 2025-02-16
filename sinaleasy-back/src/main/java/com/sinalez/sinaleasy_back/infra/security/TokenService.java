package com.sinalez.sinaleasy_back.infra.security;

// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.services.logic.UserService;

@Service
public class TokenService {
    @Autowired
    private UserService userService;

    // injetando a secret que esta na app.properties
    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // o HMAC256 recebe uma secret para adicionar algo a mais, a fim de evitar um possivel mapeamento invasor utilizando brutal force. So a aplicacao deve conhecer a chave
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUserLogin())
                    // .withClaim("userId", user.getUserId().toString()) // Adiciona o ID do usuário como um claim
                    //.withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException("Token inválido: " + exception.getMessage());
        }
    }
    
    public UUID getUserIdFromToken(String token) {
        String userLogin = validateToken(token);
        return userService.getUserIdByUserLogin(userLogin);
    }    

    // nosso token expira em 2 hrs (time zone brasilia)
    // private Instant genExpirationDate() {
    //     return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    // }

}


// sequenceDiagram
//     participant Client
//     participant Server
//     Client->>Server: Login (credenciais)
//     Server->>Server: Gera JWT com secret
//     Server->>Client: Retorna JWT
//     Client->>Server: Requests com JWT
//     Server->>Server: Valida JWT com secret
//     alt Token válido
//         Server->>Client: Resposta OK
//     else Token inválido
//         Server->>Client: 401 Unauthorized
//     end