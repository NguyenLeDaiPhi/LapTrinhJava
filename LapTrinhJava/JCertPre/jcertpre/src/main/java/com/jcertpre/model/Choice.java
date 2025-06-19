package com.jcertpre.model;

import jakarta.persistence.*;

@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestion question;

    public String getContent() { return content; }
}
