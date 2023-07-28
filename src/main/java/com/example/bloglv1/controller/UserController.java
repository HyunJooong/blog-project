package com.example.bloglv1.controller;

import com.example.bloglv1.dto.SignupRequestDto;
import com.example.bloglv1.dto.SignupResponseDto;
import com.example.bloglv1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);

        return ResponseEntity.status(201).body(new SignupResponseDto("회원가입에 성공하셨습니다."));

    }


}