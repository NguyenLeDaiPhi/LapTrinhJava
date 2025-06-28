package com.jcertpre.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Exam_Simulation")
public class ExamSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "exam_simulation_id")
    private List<com.jcertpre.model.Question> questions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public ExamSimulation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<com.jcertpre.model.Question> getQuestions() { return questions; }
    public void setQuestions(List<com.jcertpre.model.Question> questions) { this.questions = questions; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}