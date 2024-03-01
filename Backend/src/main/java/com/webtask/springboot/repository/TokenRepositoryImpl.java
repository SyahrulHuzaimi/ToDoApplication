package com.webtask.springboot.repository;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.service.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    @Autowired
    private final DbTokenRepository repo;

    public Optional<Token> findByUser(User user){
        return repo.findByUser(user);
    }

    public Optional<Token> findByJwtToken(String jwtToken){
        return repo.findByJwtToken(jwtToken);
    }
    public Token saveToken(Token token){
        return repo.save(token);
    }
}
