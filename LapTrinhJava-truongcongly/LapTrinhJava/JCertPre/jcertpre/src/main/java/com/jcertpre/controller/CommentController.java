package com.jcertpre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcertpre.model.Comment;
import com.jcertpre.model.Post;
import com.jcertpre.repository.CommentRepository;
import com.jcertpre.repository.PostRepository;

@Controller
public class CommentController {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private CommentRepository commentRepo;

    @PostMapping("/instructor/community/comment")
    public String instructorComment(@RequestParam Long postId,
                                    @RequestParam String content) {
        Post post = postRepo.findById(postId).orElseThrow();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthorName("Instructor"); 
        comment.setPost(post);
        commentRepo.save(comment);
        return "redirect:/instructor/community";
    }
    
    @PostMapping("/learner/community/comment")
    public String learnerComment(@RequestParam Long postId,
                                 @RequestParam String content) {
        Post post = postRepo.findById(postId).orElseThrow();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthorName("Learner"); 
        comment.setPost(post);
        commentRepo.save(comment);
        return "redirect:/learner/community";
    }
}
