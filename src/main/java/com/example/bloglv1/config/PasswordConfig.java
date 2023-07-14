package com.example.bloglv1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//PasswordEncoder는 spring에서 지원하는 인터페이스이기 때문에 이것을 쓰기 위해선 새로 값을 받아와 주입해야 된다.
@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
