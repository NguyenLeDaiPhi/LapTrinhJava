package com.jcertpre.service;

import com.jcertpre.dto.CourseRequest;
import com.jcertpre.model.Course;
import com.jcertpre.model.Instructor;
import com.jcertpre.repository.CourseRepository;
import com.jcertpre.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    public String uploadCourse(CourseRequest request, Instructor instructor) {
        logger.info("Uploading course: {} by instructor: {}", request.getTitle(), instructor.getEmail());

        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            logger.warn("Upload failed: Title is required");
            return "title:Title is required";
        }
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            logger.warn("Upload failed: Description is required");
            return "description:Description is required";
        }
        if (request.getPrice() < 0) {
            logger.warn("Upload failed: Price cannot be negative");
            return "price:Price cannot be negative";
        }

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setDuration(request.getDuration());
        course.setRating(0.0);
        course.setStudentCount(0);
        course.setInstructor(instructor);
        courseRepository.save(course);
        logger.info("Course uploaded successfully: {}", request.getTitle());
        return null;
    }

    public List<Course> getCoursesByInstructor(Instructor instructor) {
        return courseRepository.findByInstructor(instructor);
    }

    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email).orElse(null);
    }
}