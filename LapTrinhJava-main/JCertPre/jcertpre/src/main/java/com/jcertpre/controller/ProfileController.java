package com.jcertpre.controller;

import com.jcertpre.model.Learner;
import com.jcertpre.service.LearnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private LearnerService learnerService;

    @GetMapping
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            model.addAttribute("error", "Please log in as a learner.");
            return "login";
        }

        String email = auth.getName();
        Learner learner = learnerService.findByEmail(email);

        if (learner != null) {
            model.addAttribute("learner", learner);
        } else {
            model.addAttribute("error", "Access denied. This page is for learners only.");
            return "access-denied";
        }

        // Show any success/error messages if present
        model.addAttribute("success", model.containsAttribute("success") ? model.asMap().get("success") : null);
        model.addAttribute("error", model.containsAttribute("error") ? model.asMap().get("error") : null);

        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@RequestParam("email") String email,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone,
                                @RequestParam("address") String address,
                                RedirectAttributes redirectAttributes) {
        try {
            learnerService.updateProfile(email, name, phone, address);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating profile: " + e.getMessage());
        }
        return "redirect:/profile";
    }
}
