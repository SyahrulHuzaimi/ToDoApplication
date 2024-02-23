package com.webtask.springboot.service;

import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.RegistrationDto;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);


    User findFirstByUsername(String username);

    void saveUser(RegistrationDto registrationDto);

    void saveUser(User user);
}
