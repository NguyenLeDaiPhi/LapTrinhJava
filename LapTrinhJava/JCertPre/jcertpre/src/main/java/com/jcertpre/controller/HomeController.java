package com.jcertpre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Loads index.html from templates/
    }

    @GetMapping("/course")
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

}
