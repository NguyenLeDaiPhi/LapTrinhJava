package com.jcertpre.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jcertpre.model.Course;
import com.jcertpre.model.Instructor;

public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByInstructor(Instructor instructor);
    Page<Course> findAll(Pageable pageable);
}
