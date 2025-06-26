package com.jcertpre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseAllController {

    @GetMapping("/CourseAll")
    public String showCourseAllPage() {
        // Trả về tên file HTML trong templates (không cần đuôi .html)
        return "CourseAll";
    }
}
