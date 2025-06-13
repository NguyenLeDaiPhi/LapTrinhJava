package com.example.jcertpre.controller;

import com.example.jcertpre.model.Course;
import com.example.jcertpre.model.Instructor;
import com.example.jcertpre.repository.CourseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public String listCourses(HttpSession session, Model model) {
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        if (instructor != null) {
            List<Course> courses = courseRepository.findByInstructor(instructor);
            model.addAttribute("courses", courses);
        }
        return "instructor-course-list";
    }
}