// Course.java
package com.example.jcertpre.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tên khóa học không được để trống")
    @Size(max = 200, message = "Tên khóa học không được vượt quá 200 ký tự")
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    @Column(name = "description", length = 1000)
    private String description;
    
    @NotNull(message = "Giá khóa học không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá khóa học phải lớn hơn 0")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "duration_hours")
    private Integer durationHours;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private CourseLevel level;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseStatus status;
    
    @Column(name = "max_students")
    private Integer maxStudents;
    
    @Column(name = "current_students")
    private Integer currentStudents = 0;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    @JsonBackReference
    private Instructor instructor;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Schedule> schedules;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Question> questions;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = CourseStatus.DRAFT;
        }
        if (currentStudents == null) {
            currentStudents = 0;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CourseLevel {
        BEGINNER("Cơ bản"),
        INTERMEDIATE("Trung cấp"),
        ADVANCED("Nâng cao");
        
        private final String displayName;
        
        CourseLevel(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum CourseStatus {
        DRAFT("Bản nháp"),
        PUBLISHED("Đã xuất bản"),
        ONGOING("Đang diễn ra"),
        COMPLETED("Đã hoàn thành"),
        CANCELLED("Đã hủy");
        
        private final String displayName;
        
        CourseStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}