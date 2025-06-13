package com.example.jcertpre.controller;

import com.example.jcertpre.model.Instructor;
import com.example.jcertpre.repository.InstructorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "instructor-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Instructor instructor = instructorRepository.findByEmail(email);
        if (instructor != null && instructor.getPassword().equals(password)) {
            session.setAttribute("instructor", instructor);
            return "redirect:/instructor/dashboard";
        }
        model.addAttribute("error", "Email or password incorrect");
        return "instructor-login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        model.addAttribute("instructor", instructor);
        return "instructor-dashboard";
    }
}