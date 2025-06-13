package com.jcertpre.controller;

import com.jcertpre.model.Instructor;
import com.jcertpre.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/instructor")
public class InstructorAuthController {
    
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login_instructor";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor_register"; // View for instructor registration
    }

    @PostMapping("/register")
    public String registerInstructor(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword, 
            Model model) {
            
        // Check if password match
        if(!password.equals(confirmPassword)) {
            model.addAttribute("error", "Password does not match!");
            return "instructor_register";
        }

        // Check if email already exists
        if (instructorRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already exists!");
            return "instructor_register"; // Return to registration page with error
        }

        // Save instructor

        Instructor instructor = new Instructor();
        instructor.setFirstName(firstName);
        instructor.setLastName(lastName);
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructor.setPassword(passwordEncoder.encode(password));
        instructor.setIsActive(true);
        instructorRepository.save(instructor);

        model.addAttribute("success", "Registration successfull!");
        return "redirect:/instructor/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/instructor/login?logout"; // Redirect to login page after logout
    }
}

