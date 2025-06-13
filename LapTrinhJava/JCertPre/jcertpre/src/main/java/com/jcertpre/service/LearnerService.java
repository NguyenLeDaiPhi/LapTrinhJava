package com.jcertpre.service;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LearnerService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        learner.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        learnerRepository.save(learner);
        return null;
    }

    public Learner findByEmail(String email) {
        return learnerRepository.findByEmail(email);
    }

    public Learner updateProfile(String email, String name, String phone, String address) {
        Learner learner = learnerRepository.findByEmail(email);
        if (learner == null) {
            throw new IllegalArgumentException("Learner not found with email: " + email);
        }

        // Update only name, phone, and address; email remains unchanged
        learner.setName(name);
        learner.setPhone(phone);
        learner.setAddress(address);

        return learnerRepository.save(learner);
    }
}