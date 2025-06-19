package com.jcertpre.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExamAnswerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ExamQuestion question;

    @ManyToOne
    @JoinColumn(name = "exam_result_id")
    private ExamResult examResult;

    // Getters and setters (tự tạo hoặc dùng Lombok nếu có)
}
