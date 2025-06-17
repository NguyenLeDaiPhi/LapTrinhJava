package com.jcertpre.controller;

import com.jcertpre.dto.CourseRequest;
import com.jcertpre.model.Instructor;
import com.jcertpre.service.CourseService;
import com.jcertpre.service.InstructorService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;
    @Autowired
    private final CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    public InstructorController(InstructorService instructorService, CourseService courseService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login_instructor";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor_register";
    }

    @PostMapping("/register")
    public String registerInstructor(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        String error = instructorService.registerInstructor(firstName, lastName, email, phone, password, confirmPassword);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("instructor", new Instructor(firstName, lastName, email, phone));
            return "instructor_register";
        }
        model.addAttribute("success", "Registration successful! Please login.");
        return "redirect:/instructor/login";
    }

    @GetMapping("/index")
    public String ShowIndexPage() {
        return "instructor_index"; // Loads single.html from templates/
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        if (!model.containsAttribute("courseRequest")) {
            model.addAttribute("courseRequest", new CourseRequest());
        }
        model.addAttribute("errors", new HashMap<String, String>());
        logger.debug("Showing course upload form");
        return "upload_course";
    }

    @PostMapping("/upload")
    public String uploadCourse(
            @ModelAttribute("courseRequest") CourseRequest request,
            Model model,
            RedirectAttributes redirectAttributes) {
        logger.info("Course upload attempt for title: {}", request.getTitle());

        Map<String, String> errors = new HashMap<>();
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            errors.put("description", "Description is required");
        }
        if (request.getPrice() < 0) {
            errors.put("price", "Price cannot be negative");
        }

        if (!errors.isEmpty()) {
            logger.warn("Validation errors: {}", errors);
            model.addAttribute("errors", errors);
            return "upload_course";
        }

        // Get authenticated instructor
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = courseService.getInstructorByEmail(username); // Assume this method exists in CourseService
        if (instructor == null) {
            errors.put("general", "Instructor not found");
            model.addAttribute("errors", errors);
            return "upload_course";
        }

        String error = courseService.uploadCourse(request, instructor);
        if (error != null) {
            logger.warn("Upload failed: {}", error);
            String[] errorParts = error.split(":", 2);
            if (errorParts.length == 2) {
                errors.put(errorParts[0], errorParts[1]);
            } else {
                errors.put("general", error);
            }
            model.addAttribute("errors", errors);
            return "upload_course";
        }

        redirectAttributes.addFlashAttribute("success", "Course uploaded successfully!");
        logger.info("Course uploaded successfully: {}", request.getTitle());
        return "redirect:/instructor/courses";
    }

    @GetMapping("/courses")
    public String showInstructorCourses(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = courseService.getInstructorByEmail(username); // Assume this method exists
        if (instructor == null) {
            return "redirect:/instructor/login";
        }
        model.addAttribute("courses", courseService.getCoursesByInstructor(instructor));
        return "instructor_course";
    }
}