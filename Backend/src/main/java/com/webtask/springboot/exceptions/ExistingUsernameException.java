package com.webtask.springboot.exceptions;

import org.springframework.http.HttpStatus;

public class ExistingUsernameException extends RuntimeException {
    private final HttpStatus httpStatus;
    public ExistingUsernameException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
