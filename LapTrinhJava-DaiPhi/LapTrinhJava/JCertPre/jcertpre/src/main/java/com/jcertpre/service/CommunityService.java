package com.jcertpre.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jcertpre.model.Comment;
import com.jcertpre.model.Post;
import com.jcertpre.repository.CommentRepository;
import com.jcertpre.repository.PostRepository;

@Service
public class CommunityService {

    private static final Logger logger = LoggerFactory.getLogger(CommunityService.class);

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommunityService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public String createPost(String title, String content, String authorName) {
        if (title == null || title.trim().isEmpty()) return "Post title is required.";
        if (content == null || content.trim().isEmpty()) return "Post content is required.";
        if (authorName == null || authorName.trim().isEmpty()) return "Author name is required.";

        try {
            Post post = new Post();
            post.setAuthorName(authorName);
            post.setTitle(title);
            post.setContent(content);
            postRepository.save(post);
            logger.info("Post created by {}: {}", authorName, title);
            return "Post created successfully.";
        } catch (Exception e) {
            logger.error("Error creating post", e);
            return "Failed to create post.";
        }
    }

    public String createComment(Long postId, String content, String authorName) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return "Post not found";
        if (content == null || content.trim().isEmpty()) return "Comment content is required";
        if (authorName == null || authorName.trim().isEmpty()) return "Author name is required";

        try {
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setAuthorName(authorName);
            comment.setContent(content);
            commentRepository.save(comment);
            logger.info("Comment created by {} on post {}", authorName, postId);
            return "Comment created successfully.";
        } catch (Exception e) {
            logger.error("Error creating comment", e);
            return "Failed to create comment.";
        }
    }
}
