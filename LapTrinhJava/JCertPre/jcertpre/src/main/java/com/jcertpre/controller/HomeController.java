package com.jcertpre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.jcertpre.repository.CommentRepository;
import com.jcertpre.repository.PostRepository;

@Controller
public class HomeController {

     @Autowired
    private PostRepository postRepo;

    @Autowired
    private CommentRepository commentRepo;
    // Hiển thị trang chủ và các trang khác 
    // Trang chủ
    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Loads index.html from templates/
    }
 
    @GetMapping("/home-course")
    public String showCoursePage() {
        return "course"; // Loads index.html from templates/
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about"; // Loads about.html from templates/
    }

    @GetMapping("/teacher")
    public String showTeacherPage() {
        return "teacher"; // Loads teacher.html from templates/
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Loads contact.html from templates/
    }

    @GetMapping("/blog")
    public String showBlogPage() {
        return "blog"; // Loads blog.html from templates/
    }

    @GetMapping("/single")
    public String showSinglePage() {
        return "single"; // Loads single.html from templates/
    }

    @GetMapping("/learnercommunity")
    public String redirectToLearnerCommunity() {
        return "redirect:/learner/community";
    }
}
