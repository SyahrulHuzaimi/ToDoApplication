package com.webtask.springboot.service;

import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.RegistrationDto;
import com.webtask.springboot.security.SecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) return Optional.empty();
        return user;
    }

    public void saveUser(RegistrationDto registrationDto){
        userRepository.saveUser(registrationDto);
    }

    public boolean userExists(String username) {
        return findUserByUsername(username).isPresent();
    }

    public void saveUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecurityConfiguration.passwordEncoder().encode(password));
        user.setRoles("ROLE_USER");
        userRepository.saveUser(user);
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }
}
