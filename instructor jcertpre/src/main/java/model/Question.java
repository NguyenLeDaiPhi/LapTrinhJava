// Question.java
package com.example.jcertpre.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tiêu đề câu hỏi không được để trống")
    @Size(max = 500, message = "Tiêu đề không được vượt quá 500 ký tự")
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    
    @NotBlank(message = "Nội dung câu hỏi không được để trống")
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;
    
    @NotBlank(message = "Tên người hỏi không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;
    
    @Column(name = "student_email")
    private String studentEmail;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private QuestionStatus status = QuestionStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private QuestionPriority priority = QuestionPriority.MEDIUM;
    
    @Column(name = "is_public")
    private Boolean isPublic = true;
    
    @Column(name = "answered_at")
    private LocalDateTime answeredAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @NotNull(message = "Khóa học không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = QuestionStatus.PENDING;
        }
        if (priority == null) {
            priority = QuestionPriority.MEDIUM;
        }
        if (isPublic == null) {
            isPublic = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (answer != null && answeredAt == null) {
            answeredAt = LocalDateTime.now();
            status = QuestionStatus.ANSWERED;
        }
    }
    
    public enum QuestionStatus {
        PENDING("Chờ trả lời"),
        ANSWERED("Đã trả lời"),
        CLOSED("Đã đóng");
        
        private final String displayName;
        
        QuestionStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum QuestionPriority {
        LOW("Thấp"),
        MEDIUM("Trung bình"),
        HIGH("Cao"),
        URGENT("Khẩn cấp");
        
        private final String displayName;
        
        QuestionPriority(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}