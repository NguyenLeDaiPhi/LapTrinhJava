package com.jcertpre.service;

import com.jcertpre.model.Course;
import com.jcertpre.model.Instructor;
import com.jcertpre.repository.InstructorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private InstructorRepository instructorRepository;
    private PasswordEncoder passwordEncoder;

    public InstructorService(InstructorRepository instructorRepository, PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerInstructor(String firstName, String lastName, String email, String phone, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Password does not match!";
        }
        if (instructorRepository.findByEmail(email).isPresent()) {
            return "Email already reigstered";
        }
        Instructor instructor = new Instructor();
        instructor.setFirstName(firstName);
        instructor.setLastName(lastName);
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructor.setPassword(passwordEncoder.encode(password));
        instructor.setIsActive(true);
        instructorRepository.save(instructor);
        return null;
    }

    public Optional<Instructor> findByEmail(String email) {
        return instructorRepository.findByEmail(email);
    }

    public List<Course> findCoursesByInstructor(Instructor instructor) {
        return instructor.getCourses();
    }
}