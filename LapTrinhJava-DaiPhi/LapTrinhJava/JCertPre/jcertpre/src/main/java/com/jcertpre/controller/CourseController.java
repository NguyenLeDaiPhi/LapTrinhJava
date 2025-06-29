package com.jcertpre.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jcertpre.model.Course;
import com.jcertpre.model.Learner;
import com.jcertpre.service.CourseService;
import com.jcertpre.service.LearnerService;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private LearnerService learnerService;

    // Hiển thị danh sách khóa học phân trang
    @GetMapping("/course")
    public String showCoursePage(Model model,
                                 @RequestParam(defaultValue = "0") int page) {
        int size = 4; // Số khóa học mỗi trang

        Pageable pageable = PageRequest.of(page, size); 
        Page<Course> coursePage = courseService.getAllCoursesPaginated(pageable);

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        return "course";
    }

    // Đăng ký khóa học (POST)
    @PostMapping("/course/register/{id}")
    public String registerCourse(@PathVariable("id") Long courseId,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {

        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "Please log in to register for a course.");
            return "redirect:/login";
        }

        String learnerEmail = authentication.getName();

        try {
            courseService.enrollLearnerInCourse(learnerEmail, courseId);
            redirectAttributes.addFlashAttribute("success", "Successfully enrolled in the course! It has been added to your profile.");
            return "redirect:/profile"; // Chuyển hướng đến trang profile
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/course";
        }
    }

    @GetMapping("/learner/course/{id}")
    public String showLearnerCourseDetail(@PathVariable("id")
                                          Long courseId, 
                                          Model model, 
                                          Principal principal, 
                                          RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to view your course.");
            return "redirect:/login";
        }

        Learner learner = learnerService.findByEmail(principal.getName());
        if (learner == null) {
            redirectAttributes.addFlashAttribute("error", "Learner account not found.");
            return "redirect:/login";
        }

        Course course = courseService.findById(courseId);
        if (course == null) {
            redirectAttributes.addFlashAttribute("error", "Course not found.");
            return "redirect:/course";
        }

        // Security check: Is the learner enrolled in this course?
        if (learner.getCourses().stream().noneMatch(c -> c.getId().equals(courseId))) {
            redirectAttributes.addFlashAttribute("error", "You are not enrolled in this course.");
            return "redirect:/course";
        }

        model.addAttribute("course", course);
        boolean examAvailable = course.getExamSimulation() != null && !course.getExamSimulation().getQuestions().isEmpty();
        model.addAttribute("examAvailable", examAvailable);

        return "learner_course_detail"; // This will be a new template
    }
}
