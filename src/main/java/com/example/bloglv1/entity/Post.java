package com.example.bloglv1.entity;

import com.example.bloglv1.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // mappedBy post는 comment에서 선언한 post로 값을 맞춰줘야 함
    private List<Comment> commentList; //댓글


    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.writer = user.getUsername();
        this.content = postRequestDto.getContent();
        this.user = user;


    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;



    }

    public void createComment(Comment comment) {
        this.commentList.add(comment);
    }
}
