// InstructorRegisterRequest.java
package com.example.jcertpre.dto;

public class InstructorRegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String expertise;
    
    // Constructors
    public InstructorRegisterRequest() {}
    
    public InstructorRegisterRequest(String username, String password, String fullName, 
                                   String email, String phone, String expertise) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.expertise = expertise;
    }
    
    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }
}