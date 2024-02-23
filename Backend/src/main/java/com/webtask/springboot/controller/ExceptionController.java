package com.webtask.springboot.controller;

import com.webtask.springboot.dto.ResponseDto;
import com.webtask.springboot.exceptions.ExistingUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {ExistingUsernameException.class})
    public ResponseEntity<?> existingUsername(){
        return new ResponseEntity<>(new ResponseDto("Username already exists."), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<?> wrongCredentials(){
        return new ResponseEntity<>(new ResponseDto("Wrong Username and Password."), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = {JwtException.class})
//    public ResponseEntity<?> wrongJwtToken(){
//        return new ResponseEntity<>(new ResponseDto("Something went wrong, try to login again."), HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(Exception.class)
//    public void findProblem(Exception ex){
//        if(ex instanceof AuthenticationException){
//            System.out.println("Auth problem");
//        }
//        if(ex instanceof ExistingUsernameException){
//            System.out.println("Username exists problem");
//        }
//        if(ex instanceof JWTDecodeException){
//            System.out.println("JWT Token problem");
//        }
//    }
}
