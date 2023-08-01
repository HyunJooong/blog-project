package com.example.bloglv1.dto;

import com.example.bloglv1.entity.Comment;
import com.example.bloglv1.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentResponseDto {

    private String writer;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public CommentResponseDto(Comment comment) {


        this.writer = comment.getUser().getUsername();
        this.comment = comment.getCommentContent();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();


    }

}
