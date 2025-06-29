package com.jcertpre.service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.util.StringUtils;

@Service
public class LearnerAdminService implements ILearnerService {

    private final PasswordEncoder passwordEncoder;
    private final LearnerRepository learnerRepository;

    public LearnerAdminService(LearnerRepository learnerRepository, PasswordEncoder passwordEncoder) {
        this.learnerRepository = learnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    @Override
    public Learner getLearnerById(Long id) {
        return learnerRepository.findById(id).orElse(null);
    }

    @Transactional
    public Learner saveLearner(Learner learnerFromForm) {
        // New learner (ID is null)
        if (learnerFromForm.getId() == null) {
            learnerFromForm.setPassword(passwordEncoder.encode(learnerFromForm.getPassword()));
            if (!StringUtils.hasText(learnerFromForm.getRole())) {
                learnerFromForm.setRole("USER");
            }
            return learnerRepository.save(learnerFromForm);
        } else {
            // Existing learner: only update password if a new one is provided
            Learner existingLearner = learnerRepository.findById(learnerFromForm.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Learner not found with id: " + learnerFromForm.getId()));

            existingLearner.setName(learnerFromForm.getName());
            existingLearner.setEmail(learnerFromForm.getEmail());
            existingLearner.setPhone(learnerFromForm.getPhone());
            existingLearner.setAddress(learnerFromForm.getAddress());
            existingLearner.setRole(learnerFromForm.getRole());

            // Only encode and set the password if a new one was entered in the form
            if (StringUtils.hasText(learnerFromForm.getPassword())) {
                existingLearner.setPassword(passwordEncoder.encode(learnerFromForm.getPassword()));
            }

            return learnerRepository.save(existingLearner);
        }
    }

    @Override
    public void deleteLearner(Long id) {
        learnerRepository.deleteById(id);
    }
}
