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

    private final LearnerService learnerService;

    @Autowired
    public ProfileController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @GetMapping
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            Learner learner = learnerService.findByEmail(email);
            model.addAttribute("learner", learner);
            return "profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    @PostMapping("/update")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            Learner updatedLearner = learnerService.updateProfile(email, name, phone, address);
            // Use RedirectAttributes to add flash attributes (available after redirect)
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            // It's not necessary to pass the learner object if index.html doesn't display profile details
            // If you need it, you can add: redirectAttributes.addFlashAttribute("learner", updatedLearner);

            return "redirect:/"; // <--- CHANGE THIS LINE
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/"; // <--- CHANGE THIS LINE
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred during profile update.");
            e.printStackTrace();
            return "redirect:/"; // <--- CHANGE THIS LINE
        }
    }
}