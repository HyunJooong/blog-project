package com.example.bloglv1.entity;

import com.example.bloglv1.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;


    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.writer = user.getUsername();
        this.content = postRequestDto.getContent();
        this.password = postRequestDto.getPassword();


    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;

    }

}
