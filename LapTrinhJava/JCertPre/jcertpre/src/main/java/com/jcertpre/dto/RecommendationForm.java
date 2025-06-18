package com.jcertpre.dto;

import com.jcertpre.model.JapaneseLevel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RecommendationForm {
    @NotBlank(message = "Full name cannot be left blank")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email! Please enter a valid email address.")
    private String email;

    @NotNull(message = "Please select current level")
    private JapaneseLevel currentLevel;

    @NotNull(message = "Please select target level")
    private JapaneseLevel targetLevel;

    // getters/setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public JapaneseLevel getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(JapaneseLevel currentLevel) { this.currentLevel = currentLevel; }

    public JapaneseLevel getTargetLevel() { return targetLevel; }
    public void setTargetLevel(JapaneseLevel targetLevel) { this.targetLevel = targetLevel; }
}
