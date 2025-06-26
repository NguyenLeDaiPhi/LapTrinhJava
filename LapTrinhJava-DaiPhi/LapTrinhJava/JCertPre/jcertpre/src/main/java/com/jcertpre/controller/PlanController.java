package com.jcertpre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlanController {

    @GetMapping("/createPlan")
    public String showCreatePlan() {
        // Trả về template createPlan.html
        return "createPlan";
    }
}
