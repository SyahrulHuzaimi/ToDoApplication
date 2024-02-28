package com.webtask.springboot.controller;

import com.webtask.springboot.security.UserPrincipal;
import com.webtask.springboot.service.AuthService;
import com.webtask.springboot.service.TaskService;
import com.webtask.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "Admin Lounge, Hello " + userPrincipal.getUsername() + ", ID: " + userPrincipal.getUserId();
    }
}
