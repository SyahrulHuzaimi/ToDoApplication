package com.webtask.springboot.controller;

import com.webtask.springboot.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return "Admin Lounge, Hello " + userPrincipal.getUsername() + ", ID: " + userPrincipal.getUserId();
    }
}
