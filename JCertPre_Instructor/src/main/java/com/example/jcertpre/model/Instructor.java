package com.example.jcertpre.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String specialization;
    private String bio;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;

    @OneToMany(mappedBy = "instructor")
    private List<Schedule> schedules;

    // Getters and setters omitted for brevity
}