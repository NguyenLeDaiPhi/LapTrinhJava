package com.jcertpre.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ExamQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examType;

    @Column(columnDefinition = "TEXT")
    private String question;

    private String correctAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Choice> choices;

    public Long getId() { return id; }
    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
    public List<Choice> getChoices() { return choices; }
}
