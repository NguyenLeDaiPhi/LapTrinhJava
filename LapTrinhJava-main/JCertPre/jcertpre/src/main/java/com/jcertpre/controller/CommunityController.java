package com.jcertpre.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcertpre.model.Comment;
import com.jcertpre.model.Post;
import com.jcertpre.repository.CommentRepository;
import com.jcertpre.repository.PostRepository;

@Controller
public class CommunityController {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping("/instructor/community")
    public String showInstructorPage(Model model) {
        List<Post> posts = postRepo.findAll();
        Map<Long, List<Comment>> postCommentsMap = getCommentsByPost(posts);

        model.addAttribute("posts", posts);
        model.addAttribute("postCommentsMap", postCommentsMap);
        return "instructorcommunity";
    }

    @GetMapping("/learner/community")
    public String showLearnerPage(Model model) {
        List<Post> posts = postRepo.findAll();
        Map<Long, List<Comment>> postCommentsMap = getCommentsByPost(posts);

        model.addAttribute("posts", posts);
        model.addAttribute("postCommentsMap", postCommentsMap);
        return "learnercommunity";
    }

    @PostMapping("/instructor/community/post")
    public String createPost(@RequestParam String title,
                             @RequestParam String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthorName("Instructor"); 
        postRepo.save(post);
        return "redirect:/instructor/community";
    }
     @PostMapping("/learner/community/comment2")
     public String createComment(@RequestParam Long postId, @RequestParam String content) {
        Comment comment = new Comment();
        Post post = postRepo.findById(postId).orElseThrow();
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setAuthorName("Learner");
        comment.setCommentedAt(LocalDateTime.now());
        commentRepo.save(comment);
         return "redirect:/learner/community";
}

    private Map<Long, List<Comment>> getCommentsByPost(List<Post> posts) {
        Map<Long, List<Comment>> map = new HashMap<>();
        for (Post post : posts) {
            List<Comment> comments = commentRepo.findByPostId(post.getId());
            map.put(post.getId(), comments);
        }
        return map;
    }
}

