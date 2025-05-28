package com.jcertpre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcertpre.model.Learner;
import com.jcertpre.service.LearnerService;

@Controller
public class RegisterController {

    @Autowired
    private LearnerService learnerService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // file templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password) {

        Learner learner = new Learner(name, email, password);
        learnerService.register(learner);

        return "redirect:/login";
    }
}
