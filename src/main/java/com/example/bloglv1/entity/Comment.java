package com.example.bloglv1.entity;

import com.example.bloglv1.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "comments")
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // 게시글에 댓글을 달기 때문에 게시글과 연관 관계 추가
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 한 명의 사용자가 여러 댓글을 달기 때문에 사용자와 연관 관계 추가
    private User user;


   public Comment(CommentRequestDto commentContent){

       this.commentContent = commentContent.getComment();

   }

   //본문 수정을 위해
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    //연관 관계 할당
    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
