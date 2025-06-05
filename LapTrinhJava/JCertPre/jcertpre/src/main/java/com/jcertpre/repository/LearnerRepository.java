package com.jcertpre.repository;

import com.jcertpre.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Learner findByEmail(String email);
    boolean existsByEmail(String email);
}
