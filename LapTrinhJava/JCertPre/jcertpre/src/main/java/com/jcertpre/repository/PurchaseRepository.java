package com.jcertpre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jcertpre.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
    boolean existsByLearnerIdAndCourseId(Long learnerId, long courseId);
}
