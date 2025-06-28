// src/main/java/com/jcertpre/dto/ExamSimulationRequest.java
package com.jcertpre.dto;

import com.jcertpre.model.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExamSimulationRequest {
    @Valid
    @NotNull(message = "Question must not be null")
    private Question question = new Question();
    // Initialize with a new Question instance
    private List<Question> questions = new ArrayList<>();

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
    }
}