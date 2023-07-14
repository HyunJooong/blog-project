package com.example.bloglv1.dto;

import com.example.bloglv1.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
public class SignupRequestDto {

    // 회원가입 유효성 검사
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "최소 4자 이상, 10자 이하이며 알파벳 소문자, 숫자로 구성")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{7,15}$",
            message = "최소 7  자 이상, 15자 이하이며 알파벳 대소문자, 숫자로 구성")
    private String password;

    @NotBlank
    private UserRoleEnum role;
}
