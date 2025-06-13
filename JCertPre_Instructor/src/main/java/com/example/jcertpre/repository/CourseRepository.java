package com.example.jcertpre.repository;

import com.example.jcertpre.model.Course;
import com.example.jcertpre.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(Instructor instructor);
    List<Course> findByInstructorAndLevel(Instructor instructor, String level);
}