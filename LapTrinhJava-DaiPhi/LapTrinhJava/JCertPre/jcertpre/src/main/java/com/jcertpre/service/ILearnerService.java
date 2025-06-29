package com.jcertpre.service;


import com.jcertpre.model.Learner;
import java.util.List;

public interface ILearnerService {
    List<Learner> getAllLearners();
    Learner getLearnerById(Long id);
    Learner saveLearner(Learner learner);
    void deleteLearner(Long id);
}

