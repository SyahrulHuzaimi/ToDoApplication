package com.webtask.springboot.service;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.LoginResponse;
import com.webtask.springboot.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

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

        //5 minutes
        long sessionDelay = 1000 * 60 * 5;
        var accessToken = jwtService.issueSession(principal.getUserId(), principal.getUsername(), roles, sessionDelay);
        //1 week
        long refreshDelay = 1000 * 60 * 60 * 24 * 7;
        var refreshToken = jwtService.issueRefresh(principal.getUserId(), principal.getUsername(), refreshDelay);

        //get user, create token object, save token to DB
        User user = userService.findUserByUsername(principal.getUsername()).orElseThrow();
        Token token = Token.builder()
                .jwtToken(refreshToken)
                .user(user)
                .build();
        jwtService.saveToken(token);


        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isAdmin(roles.toString().contains("ADMIN"))
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        System.out.println("Header= " + authHeader);
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        System.out.println("username: " + username);
        if(username != null){
            var user = userService.findUserByUsername(username).orElseThrow();
            //save token to DB
        }
    }
}
