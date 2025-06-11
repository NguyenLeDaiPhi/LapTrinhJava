// InstructorRepository.java
package com.example.jcertpre.repository;
import com.example.jcertpre.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    
    Optional<Instructor> findByEmail(String email);
    
    Optional<Instructor> findByUsername(String username);
    
    List<Instructor> findByNameContainingIgnoreCase(String name);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
    
    @Query("SELECT i FROM Instructor i WHERE i.isActive = true")
    List<Instructor> findAllActiveInstructors();
    
    @Query("SELECT i FROM Instructor i JOIN i.courses c WHERE c.id = :courseId")
    List<Instructor> findByCourseId(@Param("courseId") Long courseId);
}