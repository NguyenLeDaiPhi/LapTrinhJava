// QuestionController.java
package com.example.jcertpre.controller;
import com.example.jcertpre.dto.QuestionResponseRequest;
import com.example.jcertpre.service.QuestionService;
import com.example.jcertpre.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    @PostMapping("/{questionId}/respond")
    public ResponseEntity<String> respondToQuestion(
            @PathVariable Long questionId, 
            @RequestBody QuestionResponseRequest request) {
        questionService.respondToQuestion(questionId, request);
        return ResponseEntity.ok("Trả lời câu hỏi thành công");
    }
    
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Question>> getQuestionsByCourse(@PathVariable Long courseId) {
        List<Question> questions = questionService.getQuestionsByCourse(courseId);
        return ResponseEntity.ok(questions);
    }
    
    @GetMapping("/unanswered")
    public ResponseEntity<List<Question>> getUnansweredQuestions() {
        List<Question> questions = questionService.getUnansweredQuestions();
        return ResponseEntity.ok(questions);
    }
}