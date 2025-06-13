package com.jcertpre.dto;

import com.jcertpre.model.JapaneseLevel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RecommendationForm {
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotNull(message = "Vui lòng chọn mức hiện tại")
    private JapaneseLevel currentLevel;

    @NotNull(message = "Vui lòng chọn mức mục tiêu")
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
