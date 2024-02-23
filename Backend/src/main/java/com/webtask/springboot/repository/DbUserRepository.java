package com.webtask.springboot.repository;

import com.webtask.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DbUserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    User findFirstByUsername(String username);
}
