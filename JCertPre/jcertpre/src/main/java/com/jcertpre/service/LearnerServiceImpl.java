package com.jcertpre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
// Implementation LearnerServiceImpl
@Service
public class LearnerServiceImpl implements LearnerService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Override
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    @Override
    public Learner createLearner(Learner learner) {
        return learnerRepository.save(learner);
    }
    @Override
    public Learner register(Learner learner) {
        if (learnerRepository.existsByEmail(learner.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        return learnerRepository.save(learner);
    }

    @Override
    public boolean emailExists(String email) {
    return learnerRepository.findByEmail(email) != null;
}


}

