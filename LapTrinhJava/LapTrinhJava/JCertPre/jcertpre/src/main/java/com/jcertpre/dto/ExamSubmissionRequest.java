package com.jcertpre.dto;

import java.util.Map;

public class ExamSubmissionRequest {
    // Map of questionId to selectedAnswer (e.g., "a", "b", "c", "d")
    private Map<Long, String> answers;

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
