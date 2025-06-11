// QuestionResponseRequest.java
package com.example.jcertpre.dto;

public class QuestionResponseRequest {
    private String response;
    private Long instructorId;
    
    // Constructors
    public QuestionResponseRequest() {}
    
    public QuestionResponseRequest(String response, Long instructorId) {
        this.response = response;
        this.instructorId = instructorId;
    }
    
    // Getters and Setters
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}