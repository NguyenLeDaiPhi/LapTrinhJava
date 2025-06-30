package com.jcertpre.repository;

import com.jcertpre.model.ExamResult;
import com.jcertpre.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findByLearnerOrderBySubmittedAtDesc(Learner learner);
}
