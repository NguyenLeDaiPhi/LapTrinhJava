package com.jcertpre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcertpre.model.Learner;


public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Learner findByEmail(String email);
    boolean existsByEmail(String email);
}
