package com.webtask.springboot.controller;

import com.webtask.springboot.domain.Task;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.*;
import com.webtask.springboot.exceptions.RegistrationException;
import com.webtask.springboot.security.UserPrincipal;
import com.webtask.springboot.service.AuthService;
import com.webtask.springboot.service.TaskService;
import com.webtask.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/")
    public String greeting(){
        return "HelloWorld";
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseDto> helloResponse(){
        return new ResponseEntity<>(new ResponseDto("Hello World"), HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse loginResponse = authService.attemptLogin(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "Message received if logged in as: " + userPrincipal.getUsername() + ". With ID: " + userPrincipal.getUserId();
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "Admin Lounge, Hello " + userPrincipal.getUsername() + ", ID: " + userPrincipal.getUserId();
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

    @GetMapping("/tasks")
    public StringTasksDto showTasks(@AuthenticationPrincipal UserPrincipal userPrincipal){
        Optional<User> optUser = userService.findUserByUsername(userPrincipal.getUsername());
        User user = optUser.get();
        List<Task> tasks= taskService.findByUser(user);
        return taskService.getStringTasks(tasks);
    }

    @PostMapping("/tasks")
    public StringTasksDto newTask(@RequestBody @Valid TaskDto taskDto, @AuthenticationPrincipal UserPrincipal userPrincipal){
        Optional<User> optUser = userService.findUserByUsername(userPrincipal.getUsername());
        User user = optUser.get();
        taskService.newTask(taskDto.getTask(), user);
        List<Task> tasks= taskService.findByUser(user);
        return taskService.getStringTasks(tasks);
    }
}
