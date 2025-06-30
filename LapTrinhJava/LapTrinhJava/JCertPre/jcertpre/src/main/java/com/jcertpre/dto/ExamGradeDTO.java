package com.jcertpre.dto;

import java.util.Map;

public class ExamGradeDTO {
    private final double score;
    private final int totalQuestions;
    private final int correctAnswers;
    private final Map<Long, Boolean> questionResults;

    public ExamGradeDTO(double score, int totalQuestions, int correctAnswers, Map<Long, Boolean> questionResults) {
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.questionResults = questionResults;
    }

    public double getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public Map<Long, Boolean> getQuestionResults() {
        return questionResults;
    }
}
