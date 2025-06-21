package com.jcertpre.service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnerAdminService implements ILearnerService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Override
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    @Override
    public Learner getLearnerById(Long id) {
        return learnerRepository.findById(id).orElse(null);
    }

    @Override
    public Learner saveLearner(Learner learner) {
        return learnerRepository.save(learner);
    }

    @Override
    public void deleteLearner(Long id) {
        learnerRepository.deleteById(id);
    }
}
