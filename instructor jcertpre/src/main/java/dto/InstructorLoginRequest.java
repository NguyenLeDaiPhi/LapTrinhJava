// InstructorLoginRequest.java
package com.example.jcertpre.dto;

public class InstructorLoginRequest {
    private String username;
    private String password;
    
    // Constructors
    public InstructorLoginRequest() {}
    
    public InstructorLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}