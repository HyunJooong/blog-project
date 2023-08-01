package com.example.bloglv1.service;

import com.example.bloglv1.dto.CommentRequestDto;
import com.example.bloglv1.dto.CommentResponseDto;
import com.example.bloglv1.entity.Comment;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.entity.User;
import com.example.bloglv1.entity.UserRoleEnum;
import com.example.bloglv1.repository.CommentRepository;
import com.example.bloglv1.security.UserDetailsImpl;
import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;

    private final CommentRepository commentRepository;

    //댓글 작성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user, Long postId) {

        Post post = postService.findPost(postId);//게시글이 있는지 확인

        Comment comment = new Comment(commentRequestDto);

        //댓글 작성자(user_id)
        comment.setUser(user);
        //post_id 생성
        comment.setPost(post);

        commentRepository.save(comment); // comment에 값 저장

        post.createComment(comment); //게시글에 댓글 추가



        return new CommentResponseDto(comment);


    }

    //댓글 수정
    @Transactional // 메서드가 종료되는 시점에 DB에 반영 되도록 하는 역할
    public CommentResponseDto updateComment(Long id,
                                           CommentRequestDto commentRequestDto,
                                           User user,
                                           Long postId) {
        //게시글 존재 확인
        Post post = postService.findPost(postId);

        //댓글 존재 확인
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        //관리자 및 작성자 확인
        if (!(user.getRole().equals(UserRoleEnum.ADMIN)|| comment.getUser().equals(user))){
            throw new RejectedExecutionException("접근 권한이 없습니다.");
        }

        //댓글 수정
        comment.setCommentContent(commentRequestDto.getComment());

        return new CommentResponseDto(comment);


    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long id, User user) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글이 존재하지 않습니다."));

            //관리자 및 작성자 확인
        if (!(user.getRole().equals(UserRoleEnum.ADMIN)|| comment.getUser().equals(user))) {
            throw new RejectedExecutionException("접근 권한이 없습니다.");
        }

        //댓글 삭제
        commentRepository.delete(comment);



    }

}
