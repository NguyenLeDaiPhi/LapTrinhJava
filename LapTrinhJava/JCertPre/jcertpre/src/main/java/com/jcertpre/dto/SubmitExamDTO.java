package com.jcertpre.dto;

import java.util.Map;

public class SubmitExamDTO {
    private String examType;
    private Map<String, String> answers;

    public SubmitExamDTO() {}

    public SubmitExamDTO(String examType, Map<String, String> answers) {
        this.examType = examType;
        this.answers = answers;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }
}
