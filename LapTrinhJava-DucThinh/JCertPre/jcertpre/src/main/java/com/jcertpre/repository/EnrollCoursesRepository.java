package com.jcertpre.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollCoursesRepository {

    @Autowired
     private JdbcTemplate jdbcTemplate;
 
    public boolean existsByLearnerIdAndCourseId(Long learnerId, Long courseId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE learner_id = ? AND course_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, learnerId, courseId);
        return count != null && count > 0;
    }

    public void saveEnrollment(Long learnerId, Long courseId) {
        String sql = "INSERT INTO registrations (learner_id, course_id) VALUES (?, ?)"; 
        jdbcTemplate.update(sql, learnerId, courseId);
    }
}