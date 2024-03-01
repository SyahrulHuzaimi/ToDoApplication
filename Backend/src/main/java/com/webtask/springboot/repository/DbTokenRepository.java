package com.webtask.springboot.repository;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DbTokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser(User user);
    Optional<Token> findByJwtToken(String jwtToken);
}
