package com.jcertpre.controller;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.service.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private LearnerService learnerService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest, BindingResult result, Model model) {
    if (result.hasErrors()) {
        return "register";
    }
    String error = learnerService.registerLearner(registerRequest);
    if (error != null) {
        model.addAttribute("error", error);
        return "register";
    }
    return "registration-success.html"; // Render directly instead of redirect
}

    @GetMapping("/registration-success")
    public String showSuccessPage() {
        return "registration-success.html";
    }
}
