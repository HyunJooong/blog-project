package com.example.bloglv1.service;

import com.example.bloglv1.dto.PostRequestDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.entity.User;
import com.example.bloglv1.repository.PostRepository;
import com.example.bloglv1.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시물 작성
    public PostResponseDto createPost(User user, PostRequestDto postRequestDto) {

        //게시글 생성
        Post post = new Post(postRequestDto, user);

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
    public PostResponseDto getPost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        if (post.getWriter().equals(userDetails.getUsername())) {
            return new PostResponseDto(post);
        } else return new PostResponseDto("접근 할 수 없습니다. ");
    }


    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        //id 값 비교
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        //게시글 이름과 회원 이름 비교
       if (!post.getWriter().equals(userDetails.getUsername())) {
           throw new RejectedExecutionException();
       }
           post.update(postRequestDto.getTitle(),
                   postRequestDto.getContent());

       return new PostResponseDto(post);

    }

    //게시물 삭제
    public void deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        if (post.getWriter().equals(userDetails.getUsername())) {
            postRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("본인이 아니거나,관리자가 아니면 불가능.");

        }
    }
}
