package com.example.bloglv1.service;

import com.example.bloglv1.dto.CommentRequestDto;
import com.example.bloglv1.dto.CommentResponseDto;
import com.example.bloglv1.entity.Comment;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.entity.User;
import com.example.bloglv1.entity.UserRoleEnum;
import com.example.bloglv1.repository.CommentRepository;
import com.example.bloglv1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostService postService;

    private final CommentRepository commentRepository;

    //댓글 작성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {

        Post post = postService.findPost(commentRequestDto.getPostId());//게시글이 있는지 확인
        Comment comment = new Comment(commentRequestDto.getComment());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment); // comment에 값 저장

        return new CommentResponseDto("댓글이 생성되었습니다,", comment);


    }

    //댓글 수정
    public CommentResponseDto updateCommet(Long id,
                                           CommentRequestDto commentRequestDto,
                                           User user) {

        //댓글 존재 확인
        Comment comment = commentRepository.findById(id)
                .orElseThrow();

        //관리자 및 작성자 확인
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException("접근 권한이 없습니다.");
        }

        //댓긓 수정
        comment.setCommentContent(commentRequestDto.getComment());

        return new CommentResponseDto("댓글 수정 완료", comment);


    }

    //댓글 삭제
    public void deleteComment(Long id, User user) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow();
            //관리자 및 작성자 확인
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException("접근 권한이 없습니다.");
        }

        //댓글 삭제
        commentRepository.delete(comment);



    }

}
