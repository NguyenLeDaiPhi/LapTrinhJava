package com.example.jcertpre.repository;

import com.example.jcertpre.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByEmail(String email);
}