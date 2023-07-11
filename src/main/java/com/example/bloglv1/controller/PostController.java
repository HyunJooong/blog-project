package com.example.bloglv1.controller;

import com.example.bloglv1.dto.PostRequestDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시물 생성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    //게시물 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    //게시물 선택 조회
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    //게시물 업데이트
    @PutMapping("/post/{id}")
    public Post updatePost(@PathVariable Long id,
                           @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(id, postRequestDto);
    }

    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.deletePost(id,postRequestDto.getPassword() );
    }
}
