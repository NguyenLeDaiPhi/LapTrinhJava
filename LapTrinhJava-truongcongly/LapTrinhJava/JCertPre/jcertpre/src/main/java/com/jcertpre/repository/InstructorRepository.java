package com.jcertpre.repository;

import com.jcertpre.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findByEmail(String email);
    boolean existsByEmail(String email);
}