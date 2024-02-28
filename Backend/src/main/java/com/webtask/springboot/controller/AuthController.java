package com.webtask.springboot.controller;

import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.*;
import com.webtask.springboot.exceptions.RegistrationException;
import com.webtask.springboot.security.SecurityConfiguration;
import com.webtask.springboot.security.UserPrincipal;
import com.webtask.springboot.service.AuthService;
import com.webtask.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/")
    public String greeting(){
        return "HelloWorld";
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseDto> helloResponse(){
        return new ResponseEntity<>(new ResponseDto("Hello World"), HttpStatus.OK);
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "Message received if logged in as: " + userPrincipal.getUsername() + ". With ID: " + userPrincipal.getUserId();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse loginResponse = authService.attemptLogin(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }


    @PostMapping("/auth/register")
    public ResponseEntity<ResponseDto> register(@RequestBody @Valid RegisterRequest request){
        if(request.getPassword()==null || request.getPassword().isBlank()){
            throw new RegistrationException("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (userService.userExists(request.getUsername())){
            throw new RegistrationException("Username already exists.", HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new ResponseDto("User " + request.getUsername() + " created!"), HttpStatus.CREATED);
    }

    @PostMapping("/auth/changepassword")
    public HttpStatus changePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PasswordChangeRequestDto requestDto){
        PasswordEncoder encoder = SecurityConfiguration.passwordEncoder();
        System.out.println("Here");
        if (!userPrincipal.getUsername().equals(requestDto.getUsername())){
            System.out.println("here1");
            throw new RegistrationException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User user = userService.findUserByUsername(userPrincipal.getUsername()).orElseThrow();
        if (!encoder.matches(requestDto.getPassword(), user.getPassword())){
            System.out.println(user.getPassword());
            System.out.println(requestDto.getPassword());
            System.out.println(encoder.encode(requestDto.getPassword()));
            throw new RegistrationException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        user.setPassword(encoder.encode(requestDto.getNewPassword()));
        userService.saveUser(user);
        return HttpStatus.OK;
    }
}
