package com.jcertpre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // Render login.html for learners
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }
}