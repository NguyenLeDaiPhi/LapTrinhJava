package com.jcertpre.service;
import java.util.List;

import com.jcertpre.model.Learner;
public interface LearnerService {
    List<Learner> getAllLearners();
    Learner createLearner(Learner learner);
    Learner register(Learner learner);
    boolean emailExists(String email);
    
}

