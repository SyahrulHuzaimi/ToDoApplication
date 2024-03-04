package com.webtask.springboot.service;

import com.webtask.springboot.domain.Token;
import com.webtask.springboot.domain.User;
import com.webtask.springboot.dto.TokensResponse;
import com.webtask.springboot.exceptions.RegistrationException;
import com.webtask.springboot.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    long sessionDelay = 1000 * 60 * 5;  //5 minutes
    long refreshDelay = 1000 * 60 * 60 * 24 * 7;    //1 week

    public TokensResponse attemptLogin(String username, String password){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //extract principal
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var accessToken = jwtService.issueSession(principal.getUserId(), principal.getUsername(), roles, sessionDelay);
        var refreshToken = jwtService.issueRefresh(principal.getUserId(), principal.getUsername(), refreshDelay);

        //get user, create token object, save token to DB
        User user = userService.findUserByUsername(principal.getUsername()).orElseThrow();
        jwtService.deleteExpiredTokens();
        saveToken(refreshToken, user, refreshDelay);


        return TokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isAdmin(roles.toString().contains("ADMIN"))
                .expireDate(new Date(System.currentTimeMillis() + sessionDelay))
                .build();
    }

    private void saveToken(String refreshToken, User user, long delay) {
        Token token = Token.builder()
                .jwtToken(refreshToken)
                .expireDate(new Date(System.currentTimeMillis() + delay))
                .user(user)
                .build();
        jwtService.saveToken(token);
    }

    public TokensResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new RegistrationException("No access", HttpStatus.FORBIDDEN);
        }
        System.out.println("Header= " + authHeader);
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        System.out.println("username: " + username);
        if(username == null){
            throw new RegistrationException("No access", HttpStatus.FORBIDDEN);
        }
        var user = userService.findUserByUsername(username).orElseThrow();
        Token dbToken = jwtService.findByToken(refreshToken).orElseThrow();
        if(!user.equals(dbToken.getUser())){
            throw new RegistrationException("No access", HttpStatus.FORBIDDEN);
        }
        jwtService.deleteToken(dbToken);
        var newAccessToken = jwtService.issueSession(user.getId(), user.getUsername(), Collections.singletonList(user.getRoles()), sessionDelay);
        var newRefreshTokenString = jwtService.issueRefresh(user.getId(), user.getUsername(), refreshDelay);

        saveToken(newRefreshTokenString, user, refreshDelay);

        return TokensResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshTokenString)
                .isAdmin(user.getRoles().contains("ADMIN"))
                .expireDate(new Date(System.currentTimeMillis() + sessionDelay))
                .build();
    }
}
