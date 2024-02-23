package com.webtask.springboot.repository;

import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.RegistrationDto;
import com.webtask.springboot.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private final DbUserRepository repo;

    public UserRepositoryImpl(DbUserRepository repo) {
        this.repo = repo;
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User findFirstByUsername(String username) {
        return repo.findFirstByUsername(username);
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());
        repo.save(user);
    }

    @Override
    public void saveUser(User user) {
        repo.save(user);
    }
}
