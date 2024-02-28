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
    private static final String EXISTING_USERNAME = "username1";
    private static final String ANOTHER_USERNAME = "username2";

    public Optional<User> findUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) return Optional.empty();
        return user;
//        return user;
//        if(EXISTING_USERNAME.equalsIgnoreCase(username)) {
//            var user = new User();
//            user.setId(2L);
//            user.setUsername("username1");
//            user.setPassword("$2a$12$375lgkCT3GiLuQXelOqIw.a/cIR4dPWr3N7n5CzWBTkVgl1C8vQIi");//test
//            user.setRoles("ROLE_ADMIN");
//            return Optional.of(user);
//        } else if(ANOTHER_USERNAME.equalsIgnoreCase(username)){
//            var user = new User();
//            user.setId(3L);
//            user.setUsername("username2");
//            user.setPassword("$2a$12$375lgkCT3GiLuQXelOqIw.a/cIR4dPWr3N7n5CzWBTkVgl1C8vQIi");//test
//            user.setRoles("ROLE_USER");
//            return Optional.of(user);
//        }
//        return Optional.empty();
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
