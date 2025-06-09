package com.jcertpre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;

@Service
public class LearnerService {
    @Autowired
    private LearnerRepository learnerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // Enable PasswordEncoder

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
        learner.setPassword(passwordEncoder.encode(request.getPassword())); // Encode password
        learner.setRole("LEARNER");
        learnerRepository.save(learner);
        return null;
    }
}
