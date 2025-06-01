package com.jcertpre.service;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnerService {
    @Autowired
    private LearnerRepository learnerRepository;

    // Temporarily remove PasswordEncoder
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    public String registerLearner(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }
        if (!request.isAgreeTerms()) {
            return "You must agree to the terms of service";
        }
        if (learnerRepository.existsByEmail(request.getEmail())) {
            return "Email already in use";
        }
        Learner learner = new Learner();
        learner.setName(request.getName());
        learner.setEmail(request.getEmail());
        learner.setPassword(request.getPassword()); // Temporarily store plain password
        learner.setRole("LEARNER");
        learnerRepository.save(learner);
        return null;
    }
}

