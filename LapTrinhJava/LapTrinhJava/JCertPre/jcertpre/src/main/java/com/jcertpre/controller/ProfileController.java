package com.jcertpre.controller;

import com.jcertpre.model.ExamResult;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jcertpre.model.Course;
import com.jcertpre.repository.ExamResultRepository;
import com.jcertpre.model.Learner;
import com.jcertpre.service.LearnerService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private ExamResultRepository examResultRepository;

    @GetMapping
    public String showProfile(Model model, @RequestParam(defaultValue = "0") int page) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
        model.addAttribute("error", "Please log in as a learner.");
        return "login";
    }

    String email = auth.getName();
    Learner learner = learnerService.findByEmail(email);

    if (learner != null) {
        // Pagination logic
        int pageSize = 2; // 2 courses per page
        List<Course> enrolledCourses = learner.getCourses();
        Pageable pageable = PageRequest.of(page, pageSize);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), enrolledCourses.size());

        List<Course> pageContent = (start > enrolledCourses.size())
            ? Collections.emptyList()
            : enrolledCourses.subList(start, end);

        Page<Course> coursePage = new PageImpl<>(pageContent, pageable, enrolledCourses.size());
        model.addAttribute("learner", learner);
        model.addAttribute("coursePage", coursePage);

        // Fetch and add exam results to the model
        List<ExamResult> examResults = examResultRepository.findByLearnerOrderBySubmittedAtDesc(learner);
        model.addAttribute("examResults", examResults);
    } else {
        model.addAttribute("error", "Access denied. This page is for learners only.");
        return "access-denied";
    }

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

    @PostMapping("/unenroll/{courseId}")
    public String unenrollCourse(@PathVariable Long courseId,
                                 RedirectAttributes redirectAttributes,
                                 Authentication authentication) {
        String email = authentication.getName();
        try {
            learnerService.unenrollFromCourse(email, courseId);
            redirectAttributes.addFlashAttribute("success", "Successfully unenrolled from the course.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error unenrolling: " + e.getMessage());
        }
        // Redirect back to the profile page, preserving the current page if needed
        return "redirect:/profile";
    }
}
