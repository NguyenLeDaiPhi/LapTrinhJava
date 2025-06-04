package com.jcertpre.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jcertpre.service.EnrollCoursesService;

@Controller
@RequestMapping("/enroll")
public class EnrollCoursesController {

    @Autowired
    private EnrollCoursesService enrollCoursesService;

    @PostMapping
    public String enrollCourses(@RequestParam("courseId") Long courseId, // Đổi tên parameter cho rõ ràng
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Long learnerId = (Long) session.getAttribute("learnerId");
        if (learnerId == null) {
            return "redirect:/login";
        }

        boolean success = enrollCoursesService.enrollCoursesLearnerToCourses(learnerId, courseId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "You have already registered for this course!");
        }

        return "redirect:/course"; 
    }
}