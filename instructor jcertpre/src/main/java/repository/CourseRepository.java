// CourseRepository.java
package com.example.jcertpre.repository;
import com.example.jcertpre.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    List<Course> findByTitleContainingIgnoreCase(String title);
    
    List<Course> findByInstructorId(Long instructorId);
    
    List<Course> findByCategory(String category);
    
    @Query("SELECT c FROM Course c WHERE c.isActive = true")
    List<Course> findAllActiveCourses();
    
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND c.category = :category")
    List<Course> findActiveCoursesByCategory(@Param("category") String category);
    
    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId AND c.isActive = true")
    List<Course> findActiveCoursesByInstructor(@Param("instructorId") Long instructorId);
    
    @Query("SELECT COUNT(c) FROM Course c WHERE c.instructor.id = :instructorId")
    Long countCoursesByInstructor(@Param("instructorId") Long instructorId);
}