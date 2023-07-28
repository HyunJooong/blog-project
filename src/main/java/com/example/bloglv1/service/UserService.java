package com.example.bloglv1.service;

import com.example.bloglv1.dto.SignupRequestDto;
import com.example.bloglv1.entity.User;
import com.example.bloglv1.entity.UserRoleEnum;
import com.example.bloglv1.jwt.JwtUtil;
import com.example.bloglv1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername(); // 유저 아이디
        String password = passwordEncoder.encode(requestDto.getPassword()); //암호화 처리
        UserRoleEnum role = requestDto.getRole(); //권한

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);


    }

    }


