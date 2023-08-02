package com.example.bloglv1.service;

import com.example.bloglv1.dto.PostRequestDto;
import com.example.bloglv1.dto.PostResponseDto;
import com.example.bloglv1.entity.*;
import com.example.bloglv1.repository.CommentRepository;
import com.example.bloglv1.repository.LikeRepository;
import com.example.bloglv1.repository.PostRepository;
import com.example.bloglv1.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;


    //게시물 작성
    public PostResponseDto createPost(User user, PostRequestDto postRequestDto) {

        //게시글 생성
        Post post = new Post(postRequestDto, user);

        //게시글 저장
        postRepository.save(post);

        return new PostResponseDto(post);

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

    public PostResponseDto getPost(Long id, User user) {

        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));


        return new PostResponseDto(post);

    }


    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        //id 값 비교
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));

        //로그인한 username 받아오기
        User writer = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));


        //관리자 또는 작성자인지 체크
        if (!(post.getUser().getUsername().equals(writer.getUsername())
                || user.getRole().equals(UserRoleEnum.ADMIN))) {
            throw new RejectedExecutionException("수정할 수 없습니다.");
        }

        //업데이트
        post.update(postRequestDto.getTitle(),
                postRequestDto.getContent());

        return new PostResponseDto(post);

    }

    //게시물 삭제
    public void deletePost(Long id, User user) {
        //게시글 존재 체크
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
        //로그인한 유저 확인
        User writer = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));


        //관리자 또는 작성자인지 체크
        if (!(post.getUser().getUsername().equals(writer.getUsername())
                || user.getRole().equals(UserRoleEnum.ADMIN))) {
            throw new RejectedExecutionException("삭제할 수 없습니다.");
        }

        postRepository.delete(post); //게시물 삭제


    }


    public Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }


    //좋아요 생성
    @Transactional
    public void clickLike(Long id, User user) {

        //게시물 존재 확인
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        if (likeRepository.findByPostAndUser(post, user).isPresent()) {
            throw new DuplicateRequestException("좋아요를 이미 누른 상태입니다.");
        } else {

            Likes likes = new Likes(post, user);
            likeRepository.save(likes);


        }
    }

    @Transactional
    public void rollbackLike(Long id, User user) {

        //게시물 확인
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));

        Optional<Likes> foundLikes = likeRepository.findByPostAndUser(post, user);

        if (foundLikes.isPresent()) {
            Likes likes = foundLikes.get();
            likeRepository.delete(likes);
        }else throw new IllegalArgumentException("좋아요를 취소할 수 없습니다.");


        }
    }




