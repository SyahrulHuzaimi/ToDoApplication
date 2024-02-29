package com.webtask.springboot.service;

import com.webtask.springboot.dto.LoginResponse;
import com.webtask.springboot.security.JwtIssuer;
import com.webtask.springboot.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    public LoginResponse attemptLogin(String username, String password){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //extract principal
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        long delay = 1000 * 60 * 5; //5 minutes
        var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles, delay);
        return LoginResponse.builder()
                .accessToken(token)
                .isAdmin(roles.toString().contains("ADMIN"))
                .build();
    }
}
