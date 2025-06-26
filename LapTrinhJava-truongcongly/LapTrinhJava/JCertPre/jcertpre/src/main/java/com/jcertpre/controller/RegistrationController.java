package com.jcertpre.controller;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.service.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private LearnerService learnerService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute("registerRequest") RegisterRequest request, Model model) {
        String error = learnerService.registerLearner(request);
        if (error != null) {
            model.addAttribute("error", error);
            return "register";
        }
        return "redirect:/registration-success";
    }
}