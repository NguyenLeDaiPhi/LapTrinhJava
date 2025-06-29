package com.jcertpre.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.jcertpre.model.Learner;
import com.jcertpre.service.ILearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/learners")
public class AdminController {

    private final ILearnerService learnerService;

    @Autowired
    public AdminController(ILearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @GetMapping("/view")
    public String viewLearners(Model model) {
        List<Learner> allLearners = learnerService.getAllLearners();
        model.addAttribute("learners", allLearners);
        return "learners";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("learner", new Learner());
        return "learner-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Learner learner = learnerService.getLearnerById(id);
        model.addAttribute("learner", learner);
        return "learner-form";
    }

 @PostMapping("/save")
    public String saveLearner(@ModelAttribute("learner") Learner learner) {
    // The service layer now handles password encoding and setting default roles.
    learnerService.saveLearner(learner);
    return "redirect:/admin/learners/view";
}


    @GetMapping("/delete/{id}")
    public String deleteLearner(@PathVariable Long id) {
        learnerService.deleteLearner(id);
        return "redirect:/admin/learners/view";
    }
}
