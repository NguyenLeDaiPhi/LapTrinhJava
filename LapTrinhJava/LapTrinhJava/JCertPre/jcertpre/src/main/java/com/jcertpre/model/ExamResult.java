package com.jcertpre.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name = "exam_results")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id", nullable = false)
    private Learner learner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    // Constructors
    public ExamResult() {
        this.submittedAt = LocalDateTime.now();
    }

    public ExamResult(Learner learner, Course course, Double score) {
        this.learner = learner;
        this.course = course;
        this.score = score;
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Learner getLearner() { return learner; }
    public void setLearner(Learner learner) { this.learner = learner; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
