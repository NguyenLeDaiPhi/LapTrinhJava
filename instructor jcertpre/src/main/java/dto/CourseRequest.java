// CourseRequest.java
package com.example.jcertpre.dto;
import java.math.BigDecimal;

public class CourseRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration; // số giờ
    private String level;     // beginner, intermediate, advanced
    private Long instructorId;
    private String category;
    
    // Constructors
    public CourseRequest() {}
    
    public CourseRequest(String name, String description, BigDecimal price, 
                        Integer duration, String level, Long instructorId, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.level = level;
        this.instructorId = instructorId;
        this.category = category;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}