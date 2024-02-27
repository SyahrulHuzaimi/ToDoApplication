package com.webtask.springboot.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private final String accessToken;
    private final boolean isAdmin;
}
