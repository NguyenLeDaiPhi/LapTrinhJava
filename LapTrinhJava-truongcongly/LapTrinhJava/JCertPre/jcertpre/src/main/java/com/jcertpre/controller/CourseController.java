package com.jcertpre.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jcertpre.model.Course;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.CourseRepository;
import com.jcertpre.repository.LearnerRepository;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LearnerRepository learnerRepository;

    // Hiển thị danh sách khóa học phân trang
    @GetMapping("/course")
    public String showCoursePage(Model model,
                                 @RequestParam(defaultValue = "0") int page) {
        int size = 4; // Số khóa học mỗi trang

        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = courseRepository.findAll(pageable);

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        return "course";
    }

    // Đăng ký khóa học (POST)
    @PostMapping("/course/register/{id}")
    public String registerCourse(@PathVariable("id") Long courseId,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Course not found.");
            return "redirect:/course";
        }

        Course course = courseOpt.get();

        Optional<Learner> learnerOpt = learnerRepository.findByEmail(principal.getName());
        if (learnerOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Learner not found.");
            return "redirect:/course";
        }

        Learner learner = learnerOpt.get();

        if (learner.getCourses().contains(course)) {
            redirectAttributes.addFlashAttribute("error", "You are already enrolled in this course.");
        } else {
            learner.getCourses().add(course);
            learnerRepository.save(learner);
            redirectAttributes.addFlashAttribute("success", "Successfully registered for the course!");
        }

        return "redirect:/course";
    }
}