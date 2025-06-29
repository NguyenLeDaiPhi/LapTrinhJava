package com.jcertpre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseAllController {

    @GetMapping("/CourseAll")
    public String showCourseAllPage() {
        return "CourseAll";
    }
}
