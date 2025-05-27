package com.jcertpre.service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnerService {
    private final LearnerRepository repository;

    public LearnerService(LearnerRepository repository) {
        this.repository = repository;
    }

    public List<Learner> getAllLearners() {
        return repository.findAll();
    }

    public Learner createLearner(Learner learner) {
        return repository.save(learner);
    }
}
