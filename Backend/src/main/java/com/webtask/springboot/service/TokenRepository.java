package com.webtask.springboot.service;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TokenRepository {

    List<Token> findAllByUser(User user);
    Optional<Token> findByJwtToken(String jwtToken);

    Token saveToken(Token token);

    void deleteToken(Token token);
    void deleteAllByExpireDateBefore(Date date);
}
