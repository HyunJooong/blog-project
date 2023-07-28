package com.example.bloglv1.dto;

import com.example.bloglv1.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends PostResponseDto {

    private String writer;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public CommentResponseDto(String msg, Comment comment) {
        super(msg);
        this.writer = comment.getUser().getUsername();
        this.comment = comment.getCommentContent();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}
