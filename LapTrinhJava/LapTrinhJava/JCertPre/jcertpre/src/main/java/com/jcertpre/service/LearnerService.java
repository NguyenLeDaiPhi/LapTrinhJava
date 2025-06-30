package com.jcertpre.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.model.Course;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.CourseRepository;
import com.jcertpre.repository.LearnerRepository;

import jakarta.transaction.Transactional;

@Service
public class LearnerService {

    private final LearnerRepository learnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    public LearnerService(LearnerRepository learnerRepository,
                          PasswordEncoder passwordEncoder,
                          CourseRepository courseRepository) {
        this.learnerRepository = learnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseRepository = courseRepository;
    }

    public String registerLearner(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }
        if (!request.isAgreeTerms()) {
            return "You must agree to the terms of service";
        }
        if (learnerRepository.existsByEmail(request.getEmail())) {
            return "Email already in use";
        }

        Learner learner = new Learner();
        learner.setName(request.getName());
        learner.setEmail(request.getEmail());
        learner.setRole("USER");
        learner.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        learnerRepository.save(learner);
        return null;
    }

    public Learner findByEmail(String email) {
        Optional<Learner> learnerOpt = learnerRepository.findByEmail(email);
        return learnerOpt.orElse(null);
    }

    public Learner updateProfile(String email, String name, String phone, String address) {
        Optional<Learner> learnerOpt = learnerRepository.findByEmail(email);
        if (!learnerOpt.isPresent()) {
            throw new IllegalArgumentException("Learner not found with email: " + email);
        }
        Learner learner = learnerOpt.get();

        // Update only name, phone, and address; email remains unchanged
        learner.setName(name);
        learner.setPhone(phone);
        learner.setAddress(address);

        return learnerRepository.save(learner);
    }

    @Transactional
    public void unenrollFromCourse(String learnerEmail, Long courseId) {
        Learner learner = learnerRepository.findByEmail(learnerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Learner not found with email: " + learnerEmail));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        if (learner.getCourses().remove(course)) {
            // Decrement student count, ensuring it doesn't go below zero
            course.setStudentCount(Math.max(0, course.getStudentCount() - 1));
            learnerRepository.save(learner);
            courseRepository.save(course);
        } else {
            throw new IllegalStateException("Cannot unenroll. Learner is not enrolled in this course.");
        }
    }
}