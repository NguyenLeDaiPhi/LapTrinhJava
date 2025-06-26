package com.jcertpre.dto;

public class StudyPlanItem {
    private String title;
    private String description;
    private int durationWeeks;
    private String resources;

    public StudyPlanItem() {}
    public StudyPlanItem(String title, String description, int durationWeeks, String resources) {
        this.title = title;
        this.description = description;
        this.durationWeeks = durationWeeks;
        this.resources = resources;
    }
    // getters/setters...
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(int durationWeeks) { this.durationWeeks = durationWeeks; }
    public String getResources() { return resources; }
    public void setResources(String resources) { this.resources = resources; }
}
