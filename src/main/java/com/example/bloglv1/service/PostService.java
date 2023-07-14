package com.example.bloglv1.service;

import com.example.bloglv1.dto.PostRequestDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.entity.User;
import com.example.bloglv1.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시물 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto) {

        //게시글 생성
        Post post = new Post(postRequestDto);

        //게시글 저장
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);

    }


    //게시물 전체 조회
    public List<PostResponseDto> getPostList() {

        List<Post> postList = postRepository.findAllByOrderByCreateAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }

        return postResponseDtoList;


    }

    //게시물 선택 조회
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return new PostResponseDto(post);
    }


    //게시물 수정
    @Transactional
    public Post updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        post.checkPassword(postRequestDto.getPassword());

        post.update(postRequestDto.getTitle(),
                postRequestDto.getWriter(),
                postRequestDto.getContent(),
                postRequestDto.getPassword());

        return post;

    }

    //게시물 삭제
    public void deletePost(Long id, String password) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        post.checkPassword(password);



        postRepository.deleteById(id);

    }
}
