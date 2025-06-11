// InstructorLoginResponse.java
package com.example.jcertpre.dto;

public class InstructorLoginResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String expertise;
    private String token;
    
    // Constructors
    public InstructorLoginResponse() {}
    
    public InstructorLoginResponse(Long id, String username, String fullName, 
                                 String email, String phone, String expertise, String token) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.expertise = expertise;
        this.token = token;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}