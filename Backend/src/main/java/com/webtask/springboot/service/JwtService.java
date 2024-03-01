package com.webtask.springboot.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.webtask.springboot.domain.Token;
import com.webtask.springboot.security.JwtDecoder;
import com.webtask.springboot.security.JwtProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtDecoder jwtDecoder;
    private final TokenRepository tokenRepository;

    public String issueSession(long userId, String username, List<String> roles, long timeDelay){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + timeDelay))
                .withClaim("username", username)
                .withClaim("role", roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    public String issueRefresh(long userId, String username, long timeDelay){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + timeDelay))
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = jwtDecoder.decode(token);
        return decodedJWT.getClaim("username").asString();
    }


    public void saveToken(Token token) {
        tokenRepository.saveToken(token);
    }

    Optional<Token> findByToken(String tokenString){
        return tokenRepository.findByJwtToken(tokenString);
    }

    void deleteToken(Token token){
        tokenRepository.deleteToken(token);
    }

    @Transactional
    void deleteExpiredTokens(){
        tokenRepository.deleteAllByExpireDateBefore(new Date(System.currentTimeMillis()));
    }
}
