package com.example.bloglv1.controller;

import com.example.bloglv1.dto.PostRequestDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.entity.User;
import com.example.bloglv1.security.UserDetailsImpl;
import com.example.bloglv1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return postService.getPost(id, userDetails);

    }

    //게시물 업데이트
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                           @RequestBody PostRequestDto postRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.updatePost(id, postRequestDto, userDetails);
    }

    //게시물 삭제
    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
       postService.deletePost(id,userDetails);
    }
    PostResponseDto postResponseDto = new PostResponseDto("게시물이 삭제되었습니다.");
}
