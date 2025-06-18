package com.jcertpre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcertpre.model.Learner;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
    boolean existsByEmail(String email);
    Learner findByEmail(String email);
}


