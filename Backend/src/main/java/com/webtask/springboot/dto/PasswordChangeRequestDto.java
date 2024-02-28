package com.webtask.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequestDto {
    private String username;
    private String password;
    private String newPassword;
}
