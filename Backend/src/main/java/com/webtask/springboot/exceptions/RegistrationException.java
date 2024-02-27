package com.webtask.springboot.exceptions;

import org.springframework.http.HttpStatus;

public class RegistrationException extends RuntimeException {
    private final HttpStatus httpStatus;
    public RegistrationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
