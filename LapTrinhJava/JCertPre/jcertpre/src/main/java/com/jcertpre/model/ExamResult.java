package com.jcertpre.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examType;
    private int totalQuestions;
    private int correctAnswers;
    private int score;

    @OneToMany(mappedBy = "examResult", cascade = CascadeType.ALL)
    private List<ExamAnswerDetail> answerDetails;

    // Getter và Setter

    public Long getId() {
        return id;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<ExamAnswerDetail> getAnswerDetails() {
        return answerDetails;
    }

    public void setAnswerDetails(List<ExamAnswerDetail> answerDetails) {
        this.answerDetails = answerDetails;
    }
}
