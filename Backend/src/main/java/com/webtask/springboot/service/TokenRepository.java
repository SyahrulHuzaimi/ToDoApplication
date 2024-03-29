package com.webtask.springboot.service;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;

import java.util.Date;
import java.util.Optional;


public interface TokenRepository {
    Optional<Token> findByJwtToken(String jwtToken);

    Token saveToken(Token token);

    void deleteToken(Token token);
    void deleteAllByExpireDateBefore(Date date);

    void deleteAllByUser(User user);
}
