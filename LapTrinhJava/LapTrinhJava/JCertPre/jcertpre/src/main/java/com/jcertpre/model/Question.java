// src/main/java/com/jcertpre/model/Question.java
package com.jcertpre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @NotNull(message = "Question text must not be null")
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull(message = "Option A must not be null")
    @Column(name = "Option_A", nullable = false)
    private String optionA;

    @NotNull(message = "Option B must not be null")
    @Column(name = "Option_B", nullable = false)
    private String optionB;

    @NotNull(message = "Option C must not be null")
    @Column(name = "Option_C", nullable = false)
    private String optionC;

    @NotNull(message = "Option D must not be null")
    @Column(name = "Option_D", nullable = false)
    private String optionD;

    @NotNull(message = "Correct answer must not be null")
    @NotEmpty(message = "Correct answer must not be empty")
    @Pattern(regexp = "^[abcd]$", message = "Correct answer must be a, b, c, d")
    @Column(name = "Correct_Answer", nullable = false)
    private String correctAnswer;

    public Question() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }
    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }
    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }
    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}