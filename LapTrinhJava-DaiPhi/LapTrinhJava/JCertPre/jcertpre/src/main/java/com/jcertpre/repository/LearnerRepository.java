package com.jcertpre.repository;

import com.jcertpre.model.Learner;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Optional<Learner> findByEmail(String email);
    boolean existsByEmail(String email);
}
