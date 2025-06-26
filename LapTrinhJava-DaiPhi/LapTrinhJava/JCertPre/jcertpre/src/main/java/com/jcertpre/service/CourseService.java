package com.jcertpre.service;

import com.jcertpre.dto.CourseRequest;
import com.jcertpre.dto.ExamSimulationRequest;
import com.jcertpre.model.Course;
import com.jcertpre.model.ExamSimulation;
import com.jcertpre.model.Instructor;
import com.jcertpre.model.Question;
import com.jcertpre.repository.CourseRepository;
import com.jcertpre.repository.InstructorRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public String storeFiles(MultipartFile[] files) throws IOException {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                if (!file.getContentType().matches("application/(pdf|zip|msword|plain)")) {
                    logger.warn("Invalid file type: {}", file.getOriginalFilename());
                    return "files:Invalid file type. Must be PDF, ZIP, DOC, or TXT";
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    logger.warn("File too large: {}", file.getOriginalFilename());
                    return "files:File size must not exceed 10MB";
                }
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                logger.info("File stored successfully: {}", fileName);
            }
        }
        return null; // Success
    }

    public void updateCourse(Course course, MultipartFile[] files) throws IOException {
        if (course == null || course.getId() == null) {
            logger.warn("Update failed: Invalid course or ID");
            throw new IllegalArgumentException("Course or ID is invalid");
        }
        String fileError = storeFiles(files);
        if (fileError != null) {
            logger.warn("Update failed due to file errors: {}", fileError);
            throw new IOException(fileError);
        }
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
                course.getFiles().add(fileName);
            }
        }
        courseRepository.save(course);
        logger.info("Course updated successfully: {}", course.getTitle());
    }

    @Transactional
    public void saveExamSimulation(Long courseId, ExamSimulationRequest examRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        ExamSimulation examSimulation = course.getExamSimulation();
        if (examSimulation == null) {
            examSimulation = new ExamSimulation();
            examSimulation.setCourse(course);
            course.setExamSimulation(examSimulation);
        }

        // Ensure Question list is initilized
        if (examSimulation.getQuestions() == null) {
            examSimulation.setQuestions(new ArrayList<>());
        }

        if (examRequest.getQuestion() != null) {
            Question question = examRequest.getQuestion();
            // Validate question fields to prevent null values
            if (question.getText() == null || question.getText().trim().isEmpty()) {
                logger.error("Invalid question: text is null or empty.");
                throw new IllegalArgumentException("Question text is required");
            }

            if (question.getOptionA() == null || question.getOptionA().trim().isEmpty()) {
                logger.error("Invalid question: optionA is null or empty");
                throw new IllegalArgumentException("Option A is required");
            }
            if (question.getOptionB() == null || question.getOptionB().trim().isEmpty()) {
                logger.error("Invalid question: optionB is null or empty");
                throw new IllegalArgumentException("Option B is required");
            }
            if (question.getOptionC() == null || question.getOptionC().trim().isEmpty()) {
                logger.error("Invalid question: optionC is null or empty");
                throw new IllegalArgumentException("Option C is required");
            }
            if (question.getOptionD() == null || question.getOptionD().trim().isEmpty()) {
                logger.error("Invalid question: optionD is null or empty");
                throw new IllegalArgumentException("Option D is required");
            }
            if (question.getCorrectAnswer() == null || question.getCorrectAnswer().trim().isEmpty()) {
                logger.error("Invalid questionfindById: question.correctAnswer is null or empty");
                throw new IllegalArgumentException("Correct answer is required");
            }
            if (!question.getCorrectAnswer().matches("^[abcd]$")) {
                logger.error("Invalid correct answer: {}", question.getCorrectAnswer());
                throw new IllegalArgumentException("Correct answer must be a, b, c, or d");
            }
            logger.debug("Adding question: text={}, correctAnswer={}", 
                question.getText(), question.getCorrectAnswer());
            examSimulation.getQuestions().add(question);
            } else {
                 logger.error("Question object is null in examRequest");
                throw new IllegalArgumentException("Question data is missing");
            }
            try {
                courseRepository.save(course);
                logger.info("Saved exam simulation for courseId: {}, questions size: {}", 
                    courseId, examSimulation.getQuestions().size());
            } catch (Exception e) {
                logger.error("Failed to save exam simulation for courseId: {}: {}", 
                    courseId, e.getMessage(), e);
                throw new RuntimeException("Failed to save exam simulation: " + e.getMessage(), e);
        }
    }

    public Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public List<Course> getCoursesByInstructor(Instructor instructor) {
        return courseRepository.findByInstructor(instructor);
    }

    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email).orElse(null);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
}