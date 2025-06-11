// ScheduleRequest.java
package com.example.jcertpre.dto;
import java.time.LocalDateTime;

public class ScheduleRequest {
    private Long courseId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String description;
    private Integer maxStudents;
    
    // Constructors
    public ScheduleRequest() {}
    
    public ScheduleRequest(Long courseId, LocalDateTime startTime, LocalDateTime endTime, 
                          String location, String description, Integer maxStudents) {
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
        this.maxStudents = maxStudents;
    }
    
    // Getters and Setters
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }
}