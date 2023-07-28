package com.example.bloglv1.controller;

import com.example.bloglv1.dto.CommentRequestDto;
import com.example.bloglv1.dto.CommentResponseDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.security.UserDetailsImpl;
import com.example.bloglv1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/comment/{postId}")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long postId
                                            ) {

        return commentService.createComment(commentRequestDto, userDetails.getUser(),postId);

    }

    //댓글 단건 조회

    //댓글 수정
    @PutMapping("/comment/{postId}/{id}")
    public CommentResponseDto updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @PathVariable Long postId
                                            ) {

        return commentService.updateComment(id, commentRequestDto, userDetails.getUser(),postId);


    }

    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<PostResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PathVariable Long id){
        try {
            commentService.deleteComment(id, userDetails.getUser());
            return ResponseEntity.ok().body(new PostResponseDto("게시글 삭제 성공"));
        } catch (RejectedExecutionException e) {
            // PostResponse msg 사용
            return ResponseEntity.badRequest().body(new PostResponseDto("권한이 없습니다."));
        }
    }
}
