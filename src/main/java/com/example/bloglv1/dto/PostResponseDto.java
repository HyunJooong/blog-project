package com.example.bloglv1.dto;

import com.example.bloglv1.entity.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.content = post.getContent();
        this.password = post.getPassword();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();



    }
}
