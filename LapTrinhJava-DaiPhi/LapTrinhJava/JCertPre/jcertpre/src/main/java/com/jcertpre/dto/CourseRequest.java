package com.jcertpre.dto;

import java.util.List;

public class CourseRequest {
    private String title;
    private String description;
    private double price;
    private String duration;
    private List<String> files;

    public CourseRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public List<String> getFiles() { return files; }
    public void setFiles(List<String> files) { this.files = files; }
}