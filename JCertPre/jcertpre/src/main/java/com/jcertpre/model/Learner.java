package com.jcertpre.model;

import jakarta.persistence.*;

public class Learner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and Setters
}
