package com.example.bloglv1.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

    private Long postId; // 게시글 확인
    private final String comment; // 댓글


}
