package com.example.bloglv1.controller;

import com.example.bloglv1.dto.PostRequestDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.security.UserDetailsImpl;
import com.example.bloglv1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

//Controller vs RestController
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시물 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody PostRequestDto postRequestDto) {

        return postService.createPost(userDetails.getUser(), postRequestDto);
    }

    //게시물 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    //게시물 선택 조회
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPost(id, userDetails.getUser());

    }

    //게시물 업데이트
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {


        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    //게시물 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new PostResponseDto("게시글 삭제 성공"));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new PostResponseDto("권한이 없습니다."));
        }
    }

    //게시물 좋아요 기능
    @PostMapping("/post/{id}/like")
    public ResponseEntity<PostResponseDto> clickLike(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            postService.clickLike(id, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new PostResponseDto("게시글 좋아요 성공"));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new PostResponseDto("권한이 없습니다."));
        }


    }

    //게시물 좋아요 취소
//    @DeleteMapping("/post/{id}/likes")
//    public String cancelLike(@PathVariable Long id,
//                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        postService.cancelLike(id, userDetails.getUser());
//
//        return "취소되었습니다,";
//
//
//
//    }
}
