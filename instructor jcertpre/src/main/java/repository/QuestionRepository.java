// QuestionRepository.java
package com.example.jcertpre.repository;
import com.example.jcertpre.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findByCourseId(Long courseId);
    
    List<Question> findByStudentId(Long studentId);
    
    List<Question> findByInstructorId(Long instructorId);
    
    @Query("SELECT q FROM Question q WHERE q.isAnswered = false")
    List<Question> findUnansweredQuestions();
    
    @Query("SELECT q FROM Question q WHERE q.course.id = :courseId AND q.isAnswered = false")
    List<Question> findUnansweredQuestionsByCourse(@Param("courseId") Long courseId);
    
    @Query("SELECT q FROM Question q WHERE q.instructor.id = :instructorId AND q.isAnswered = false")
    List<Question> findUnansweredQuestionsByInstructor(@Param("instructorId") Long instructorId);
    
    @Query("SELECT q FROM Question q WHERE q.student.id = :studentId ORDER BY q.createdAt DESC")
    List<Question> findQuestionsByStudentOrderByDate(@Param("studentId") Long studentId);
    
    @Query("SELECT COUNT(q) FROM Question q WHERE q.course.id = :courseId")
    Long countQuestionsByCourse(@Param("courseId") Long courseId);
    
    @Query("SELECT COUNT(q) FROM Question q WHERE q.course.id = :courseId AND q.isAnswered = false")
    Long countUnansweredQuestionsByCourse(@Param("courseId") Long courseId);
}