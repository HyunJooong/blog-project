package com.example.bloglv1.dto;

import com.example.bloglv1.entity.Comment;
import com.example.bloglv1.entity.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private List<CommentResponseDto> commentList;
    private Integer likeCount;


    private String msg; //메시지

    public PostResponseDto(String msg) {
        this.msg = msg;
    }

    public PostResponseDto(Post post) {

        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.content = post.getContent();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = post.getCommentList().stream()
                .map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreateAt).reversed())
                .toList();
        this.likeCount = post.getLikesList().size();

    }
}
