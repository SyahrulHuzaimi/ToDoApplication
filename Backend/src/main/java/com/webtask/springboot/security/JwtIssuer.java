package com.webtask.springboot.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties jwtProperties;
    public String issue(long userId, String username, List<String> roles, long timeDelay){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + timeDelay))
                .withClaim("username", username)
                .withClaim("role", roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
