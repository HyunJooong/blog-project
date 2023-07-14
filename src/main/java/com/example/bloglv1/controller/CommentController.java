package com.example.bloglv1.controller;

import com.example.bloglv1.dto.CommentRequestDto;
import com.example.bloglv1.dto.CommentResponseDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.dto.SignupResponseDto;
import com.example.bloglv1.security.UserDetailsImpl;
import com.example.bloglv1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ConverterRegistration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createComment(commentRequestDto, userDetails.getUser());

    }

    //댓글 수정
    @PutMapping("/comment/{id}")
    public CommentResponseDto updataComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto) {

        return commentService.updateCommet(id, commentRequestDto, userDetails.getUser());


    }

    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<PostResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PathVariable Long id){
        try {
            commentService.deleteComment(id, userDetails.getUser());
            return ResponseEntity.ok().body(new PostResponseDto("게시글 삭제 성공"));
        } catch (RejectedExecutionException e) {
            // PosrResponse msg 사용
            return ResponseEntity.badRequest().body(new PostResponseDto("권한이 없습니다."));
        }
    }
}
