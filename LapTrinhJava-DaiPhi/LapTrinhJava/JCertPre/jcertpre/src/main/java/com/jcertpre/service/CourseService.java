package com.jcertpre.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jcertpre.dto.CourseRequest;
import com.jcertpre.dto.ExamSimulationRequest;
import com.jcertpre.dto.ExamSubmissionRequest;
import com.jcertpre.model.Course;
import com.jcertpre.model.ExamSimulation;
import com.jcertpre.model.Instructor;
import com.jcertpre.model.Learner;
import com.jcertpre.model.Question;
import com.jcertpre.repository.CourseRepository;
import com.jcertpre.repository.InstructorRepository;
import com.jcertpre.repository.LearnerRepository;

import jakarta.transaction.Transactional;
@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final LearnerRepository learnerRepository;

    public CourseService(CourseRepository courseRepository,
                         InstructorRepository instructorRepository,
                         LearnerRepository learnerRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.learnerRepository = learnerRepository;
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

    private List<String> storeFiles(MultipartFile[] files) throws IOException {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        List<String> storedFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String contentType = file.getContentType();
                if (contentType == null ||
                        !(contentType.equals("application/pdf") ||
                          contentType.equals("application/zip") ||
                          contentType.equals("application/msword") ||
                          contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") || // .docx
                          contentType.equals("text/plain"))) {
                    logger.warn("Invalid file type: {} for file {}", contentType, file.getOriginalFilename());
                    throw new IOException("Invalid file type for " + file.getOriginalFilename() + ". Must be PDF, ZIP, DOC, DOCX, or TXT");
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    logger.warn("File too large: {}", file.getOriginalFilename());
                    throw new IOException("File size for " + file.getOriginalFilename() + " must not exceed 10MB");
                }
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                storedFileNames.add(fileName);
                logger.info("File stored successfully: {}", fileName);
            }
        }
        return storedFileNames;
    }

    @Transactional
    public void updateCourse(Course course, MultipartFile[] files) throws IOException {
        if (course == null || course.getId() == null) {
            logger.warn("Update failed: Invalid course or ID");
            throw new IllegalArgumentException("Course or ID is invalid");
        }

        // Store new files and add their names to the course's existing file list
        List<String> newFileNames = storeFiles(files);
        if (course.getFiles() == null) {
            course.setFiles(new ArrayList<>());
        }
        course.getFiles().addAll(newFileNames);

        courseRepository.save(course);
        logger.info("Course updated successfully: {}", course.getTitle());
    }

    public List<Question> getExamQuestionsForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found: " + courseId));
        if (course.getExamSimulation() != null && course.getExamSimulation().getQuestions() != null) {
            return course.getExamSimulation().getQuestions();
        }
        return new ArrayList<>();
    }

    public Map.Entry<Integer, Map<Long, Boolean>> gradeExam(Long courseId, ExamSubmissionRequest submission) {
        List<Question> examQuestions = getExamQuestionsForCourse(courseId);
        Map<Long, String> submittedAnswers = submission.getAnswers();

        int score = 0;
        Map<Long, Boolean> results = new java.util.HashMap<>();

        for (Question question : examQuestions) {
            String correctAnswer = question.getCorrectAnswer();
            String learnerAnswer = submittedAnswers.get(question.getId());

            boolean isCorrect = (learnerAnswer != null && learnerAnswer.equalsIgnoreCase(correctAnswer));
            results.put(question.getId(), isCorrect);
            if (isCorrect) {
                score++;
            }
        }
        return new AbstractMap.SimpleEntry<>(score, results);
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

    public Page<Course> getAllCoursesPaginated(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Transactional
    public void enrollLearnerInCourse(String learnerEmail, Long courseId) {
        Learner learner = learnerRepository.findByEmail(learnerEmail)
                .orElseThrow(() -> new IllegalStateException("Learner not found with email: " + learnerEmail));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found with ID: " + courseId));

        // Check if already enrolled
        if (learner.getCourses().contains(course)) {
            throw new IllegalStateException("You are already enrolled in this course.");
        }

        // Enroll the learner
        learner.getCourses().add(course);
        course.setStudentCount(course.getStudentCount() + 1);

        // Save both entities to persist changes
        learnerRepository.save(learner);
        courseRepository.save(course);
    }
}