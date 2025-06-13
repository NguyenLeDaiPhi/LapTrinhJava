package com.jcertpre.controller;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import com.jcertpre.service.LearnerService;
import com.jcertpre.dto.LoginRequest;
import com.jcertpre.dto.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private LearnerRepository learnerRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model) {
        Learner learner = learnerRepository.findByEmail(loginRequest.getEmail());
        if (learner == null) {
            model.addAttribute("error", "Email not found");
            return "login";
        }
        if (!learner.getPassword().equals(loginRequest.getPassword())) {
            model.addAttribute("error", "Invalid password");
            return "login";
        }
        return "redirect:/index"; // Should redirect to index.html
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index"; // Render index.html    
    }
}