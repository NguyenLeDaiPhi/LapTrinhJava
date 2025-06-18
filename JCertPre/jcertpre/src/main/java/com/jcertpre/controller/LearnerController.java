package com.jcertpre.controller;

import com.jcertpre.model.Learner;
import com.jcertpre.service.LearnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learners")
@CrossOrigin(origins = "*")
public class LearnerController {
    private final LearnerService service;

    public LearnerController(LearnerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Learner> getAllLearners() {
        return service.getAllLearners();
    }

    @PostMapping
    public Learner createLearner(@RequestBody Learner learner) {
        return service.createLearner(learner);
    }
}
