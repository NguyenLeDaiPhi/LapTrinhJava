package com.jcertpre.service;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearnerService {

    private LearnerRepository learnerRepository;
    private PasswordEncoder passwordEncoder;

    public LearnerService(LearnerRepository learnerRepository, PasswordEncoder passwordEncoder) {
        this.learnerRepository = learnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        Optional<Learner> learnerOpt = learnerRepository.findByEmail(email);
        return learnerOpt.orElse(null);
    }

    public Learner updateProfile(String email, String name, String phone, String address) {
        Optional<Learner> learnerOpt = learnerRepository.findByEmail(email);
        if (!learnerOpt.isPresent()) {
            throw new IllegalArgumentException("Learner not found with email: " + email);
        }
        Learner learner = learnerOpt.get();

        // Update only name, phone, and address; email remains unchanged
        learner.setName(name);
        learner.setPhone(phone);
        learner.setAddress(address);

        return learnerRepository.save(learner);
    }
}