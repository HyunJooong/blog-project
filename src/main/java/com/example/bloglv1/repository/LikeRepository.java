package com.example.bloglv1.repository;

import com.example.bloglv1.entity.Likes;
import com.example.bloglv1.entity.Post;
import com.example.bloglv1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {


    Optional<Likes> findByPostAndUser(Post post, User user);

}
