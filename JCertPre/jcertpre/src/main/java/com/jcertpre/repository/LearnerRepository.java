package com.jcertpre.repository;

import com.jcertpre.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
    boolean existsByEmail(String email);
}
