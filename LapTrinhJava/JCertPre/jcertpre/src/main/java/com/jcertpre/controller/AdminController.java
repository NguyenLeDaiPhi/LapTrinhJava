package com.jcertpre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String dashboard(Model model) {
        // Truyền dữ liệu nếu cần, ví dụ:
        // model.addAttribute("totalUsers", 100);
        return "admin-dashboard";  // Sẽ tìm admin-dashboard.html trong templates
    }
}
