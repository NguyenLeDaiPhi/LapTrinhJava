package com.example.jcertpre.controller;

import com.example.jcertpre.model.Instructor;
import com.example.jcertpre.model.Schedule;
import com.example.jcertpre.repository.ScheduleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping
    public String listSchedules(HttpSession session, Model model) {
        Instructor instructor = (Instructor) session.getAttribute("instructor");
        if (instructor != null) {
            List<Schedule> schedules = scheduleRepository.findByInstructorId(instructor.getId());
            model.addAttribute("schedules", schedules);
        }
        return "instructor-schedule-list";
    }
}