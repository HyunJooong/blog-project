package com.example.bloglv1.repository;

import com.example.bloglv1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post>  findAllByOrderByCreateAtDesc();
}
