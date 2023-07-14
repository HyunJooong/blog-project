package com.example.bloglv1.dto;

import com.example.bloglv1.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private UserRoleEnum role;
}
