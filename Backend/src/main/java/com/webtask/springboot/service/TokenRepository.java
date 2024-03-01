package com.webtask.springboot.service;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;

import java.util.Optional;


public interface TokenRepository {

    Optional<Token> findByUser(User user);
    Optional<Token> findByJwtToken(String jwtToken);

    Token saveToken(Token token);
}
