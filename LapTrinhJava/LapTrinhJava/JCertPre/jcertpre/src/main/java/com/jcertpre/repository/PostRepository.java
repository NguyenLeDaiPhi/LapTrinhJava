package com.jcertpre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcertpre.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
    
}
