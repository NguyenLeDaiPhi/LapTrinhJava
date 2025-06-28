package com.jcertpre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcertpre.model.Comment;
import com.jcertpre.model.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPost(Post post);
    List<Comment> findByPostId(Long postId);
}
