package com.jcertpre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcertpre.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // thêm nếu cần các phương thức tìm kiếm khác
}