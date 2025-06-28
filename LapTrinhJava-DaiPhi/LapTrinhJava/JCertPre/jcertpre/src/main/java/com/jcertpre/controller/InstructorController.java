package com.jcertpre.controller;

import com.jcertpre.dto.CourseRequest;
import com.jcertpre.dto.ExamSimulationRequest;
import com.jcertpre.model.Comment;
import com.jcertpre.model.Course;
import com.jcertpre.model.ExamSimulation;
import com.jcertpre.model.Instructor;
import com.jcertpre.model.Notification;
import com.jcertpre.model.Post;
import com.jcertpre.model.Question;
import com.jcertpre.repository.PostRepository;
import com.jcertpre.service.CommunityService;
import com.jcertpre.service.CourseService;
import com.jcertpre.service.InstructorService;
import com.jcertpre.service.NotificationService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private final InstructorService instructorService;

    @Autowired
    private final PostRepository postRepo;

    @Autowired
    private final CourseService courseService;

    @Autowired 
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    public InstructorController(InstructorService instructorService, CourseService courseService, PostRepository postRepo) {
        this.instructorService = instructorService;
        this.courseService = courseService;
        this.postRepo = postRepo;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        logger.debug("Showing instructor login page");
        return "login_instructor";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("instructor", new Instructor());
        logger.debug("Showing instructor registration page");
        return "instructor_register";
    }

    @PostMapping("/register")
    public String registerInstructor(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        logger.info("Registration attempt for email: {}", email);
        String error = instructorService.registerInstructor(firstName, lastName, email, phone, password, confirmPassword);
        if (error != null) {
            logger.warn("Registration failed: {}", error);
            model.addAttribute("error", error);
            model.addAttribute("instructor", new Instructor(firstName, lastName, email, phone));
            return "instructor_register";
        }
        logger.info("Registration successful for email: {}", email);
        model.addAttribute("success", "Registration successful! Please login.");
        return "redirect:/instructor/login";
    }

    @GetMapping({"", "/index"})
    public String showIndexboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = courseService.getInstructorByEmail(email);
        if (instructor == null) {
            logger.error("Instructor not found for email: {}", email);
            return "redirect:/instructor/login";
        }
        model.addAttribute("courses", courseService.getCoursesByInstructor(instructor));
        return "instructor_index";
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        if (!model.containsAttribute("courseRequest")) {
            model.addAttribute("courseRequest", new CourseRequest());
        }
        model.addAttribute("errors", new HashMap<String, String>());
        logger.debug("Showing course upload form");
        return "upload_course";
    }

    @PostMapping("/upload")
    public String uploadCourse(
            @ModelAttribute("courseRequest") CourseRequest request,
            Model model,
            RedirectAttributes redirectAttributes) {
        logger.info("Course upload attempt for title: {}", request.getTitle());

        Map<String, String> errors = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null || username.equals("anonymousUser")) {
            logger.error("No authenticated user found for course upload");
        }

        Instructor instructor = courseService.getInstructorByEmail(username);
        if (instructor == null) {
            logger.error("Instructor not found for email: {}", username);
            errors.put("general", "Instructor not found");
            model.addAttribute("errors", errors);
            return "upload_course";
        }

        String error = courseService.uploadCourse(request, instructor);
        if (error != null) {
            logger.warn("Upload failed: {}", error);
            String[] errorParts = error.split(":", 2);
            if (errorParts.length == 2) {
                errors.put(errorParts[0], errorParts[1]);
            } else {
                errors.put("general", error);
            }
            model.addAttribute("errors", errors);
            return "upload_course";
        }

        redirectAttributes.addFlashAttribute("success", "Course uploaded successfully");
        logger.info("Course uploaded successfully: {}", request.getTitle());
        return "redirect:/instructor/courses";
    }

    @GetMapping("/courses")
    public String showInstructorCourses(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null || username.equals("anonymousUser")) {
            logger.error("No authenticated user found for viewing courses");
            return "redirect:/instructor/login";
        }

        Instructor instructor = courseService.getInstructorByEmail(username);
        if (instructor == null) {
            logger.error("Instructor not found for email: {}", username);
            return "redirect:/instructor/login";
        }
        model.addAttribute("courses", courseService.getCoursesByInstructor(instructor));
        return "instructor_course";
    }

    @GetMapping("/course/{courseId}")
    public String showCourseManagement(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            logger.error("Course not found for ID: {}", courseId);
            model.addAttribute("error", "Course not found");
            return "error_page"; // Assume an error page exists
        }
        model.addAttribute("course", course);
        model.addAttribute("exam", new ExamSimulationRequest()); // Ensure exam is always initialized
        return "instructor_course_management";
    }

    @PostMapping("/course/{courseId}/update")
    public String updateCourse(@PathVariable("courseId") Long courseId,
                              @ModelAttribute("course") Course course,
                              @RequestParam("files") MultipartFile[] files,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Course existingCourse = courseService.findById(courseId);
        if (existingCourse == null || !existingCourse.getInstructor().getEmail().equals(email)) {
            logger.warn("Unauthorized access or course not found for ID: {}", courseId);
            return "redirect:/instructor/dashboard";
        }

        Map<String, String> errors = new HashMap<>();
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (course.getDescription() == null || course.getDescription().trim().isEmpty()) {
            errors.put("description", "Description is required");
        }
        if (course.getPrice() == null || course.getPrice() < 0) {
            errors.put("price", "Price is required and cannot be negative");
        }

        if (!errors.isEmpty()) {
            // Preserve user input on validation failure
            existingCourse.setTitle(course.getTitle());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setPrice(course.getPrice());
            existingCourse.setDuration(course.getDuration());

            model.addAttribute("course", existingCourse);
            model.addAttribute("errors", errors);
            model.addAttribute("exam", existingCourse.getExamSimulation() != null ? existingCourse.getExamSimulation() : new ExamSimulation());
            model.addAttribute("notification", new Notification());
            return "instructor_course_management";
        }

        try {
            // Update the existing course with new details
            existingCourse.setTitle(course.getTitle());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setPrice(course.getPrice());
            existingCourse.setDuration(course.getDuration());
            courseService.updateCourse(existingCourse, files);
            redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
            logger.info("Course {} updated by {}", courseId, email);
        } catch (IOException e) {
            logger.error("Failed to update course {}: {}", courseId, e.getMessage(), e);
            errors.put("general", "Failed to update course: " + e.getMessage());
            model.addAttribute("course", existingCourse);
            model.addAttribute("errors", errors);
            model.addAttribute("exam", existingCourse.getExamSimulation() != null ? existingCourse.getExamSimulation() : new ExamSimulation());
            model.addAttribute("notification", new Notification());
            return "instructor_course_management";
        }
        return "redirect:/instructor/course/" + courseId;
    }

    @PostMapping("/course/{courseId}/notification")
    public String postNotification(@PathVariable("courseId") Long courseId,
                                  @ModelAttribute("notification") Notification notification,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Course course = courseService.findById(courseId);
        if (course == null || !course.getInstructor().getEmail().equals(email)) {
            logger.warn("Unauthorized access or course not found for ID: {}", courseId);
            return "redirect:/instructor/dashboard";
        }

        Map<String, String> errors = new HashMap<>();
        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            errors.put("message", "Notification message is required");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("course", course);
            model.addAttribute("exam", course.getExamSimulation() != null ? course.getExamSimulation() : new ExamSimulation());
            return "instructor_course_management";
        }

        try {
            notification.setUserEmail(course.getInstructor().getEmail());
            notification.setCreatedAt(java.time.LocalDateTime.now());
            notification.setCourse(course);
            notificationService.saveNotification(notification);
            redirectAttributes.addFlashAttribute("success", "Notification posted successfully!");
            logger.info("Notification posted for course {} by {}", courseId, email);
        } catch (Exception e) {
            logger.error("Failed to post notification for course {}: {}", courseId, e.getMessage(), e);
            errors.put("general", "Failed to post notification");
            model.addAttribute("errors", errors);
            model.addAttribute("course", course);
            model.addAttribute("exam", course.getExamSimulation() != null ? course.getExamSimulation() : new ExamSimulation());
            return "instructor_course_management";
        }
        return "redirect:/instructor/course/" + courseId;
    }

    @PostMapping("/course/{courseId}/exam")
    public String saveExam(@PathVariable Long courseId,
                           @Valid @ModelAttribute("exam") ExamSimulationRequest examRequest,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        logger.info("Processing exam save for courseId: {}", courseId);
        logger.debug("Received examRequest: {}", examRequest);
        if (examRequest.getQuestion() != null) {
            logger.debug("Question data: text={}, optionA={}, optionB={}, optionC={}, optionD={}, correctAnswer={}",
                    examRequest.getQuestion().getText(),
                    examRequest.getQuestion().getOptionA(),
                    examRequest.getQuestion().getOptionB(),
                    examRequest.getQuestion().getOptionC(),
                    examRequest.getQuestion().getOptionD(),
                    examRequest.getQuestion().getCorrectAnswer());
        } else {
            logger.warn("Question object is null in examRequest");
        }

        // Check for validation errors from @NotEmpty and @Pattern annotations
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
            logger.warn("Validation errors: {}", errors);
        }

        // Retrieve and validate the new question
        Question newQuestion = examRequest.getQuestion();
        if (newQuestion == null) {
            errors.put("question", "Question data is missing");
            logger.warn("Question object is null");
        } else {
            // Validate all fields
            if (newQuestion.getText() == null || newQuestion.getText().trim().isEmpty()) {
                errors.put("question.text", "Question text is required");
            }
            if (newQuestion.getOptionA() == null || newQuestion.getOptionA().trim().isEmpty()) {
                errors.put("question.optionA", "Option A is required");
            }
            if (newQuestion.getOptionB() == null || newQuestion.getOptionB().trim().isEmpty()) {
                errors.put("question.optionB", "Option B is required");
            }
            if (newQuestion.getOptionC() == null || newQuestion.getOptionC().trim().isEmpty()) {
                errors.put("question.optionC", "Option C is required");
            }
            if (newQuestion.getOptionD() == null || newQuestion.getOptionD().trim().isEmpty()) {
                errors.put("question.optionD", "Option D is required");
            }
            if (newQuestion.getCorrectAnswer() == null || newQuestion.getCorrectAnswer().trim().isEmpty()) {
                errors.put("question.correctAnswer", "Correct answer is required");
            } else if (!newQuestion.getCorrectAnswer().matches("^[abcd]$")) {
                errors.put("question.correctAnswer", "Correct answer must be a, b, c, or d");
            }
        }

        if (!errors.isEmpty()) {
            logger.warn("Validation errors: {}", errors);
            model.addAttribute("errors", errors);
            model.addAttribute("courseId", courseId);
            model.addAttribute("course", courseService.findCourseById(courseId));
            model.addAttribute("exam", examRequest);
            return "instructor_course_management";
        }

        try {
            Course course = courseService.findCourseById(courseId);
            if (course == null) {
                logger.error("Course not found for ID: {}", courseId);
                errors.put("course", "Course not found");
                model.addAttribute("errors", errors);
                model.addAttribute("courseId", courseId);
                model.addAttribute("exam", examRequest);
                return "instructor_course_management";
            }

            courseService.saveExamSimulation(courseId, examRequest);
            logger.info("Exam question saved for courseId: {}, question: {}", courseId, newQuestion.getText());
            redirectAttributes.addFlashAttribute("success", "Question added successfully!");
            return "redirect:/instructor/course/" + courseId;
        } catch (Exception e) {
            logger.error("Failed to save exam for courseId: {}: {}", courseId, e.getMessage(), e);
            errors.put("system", "Error saving exam: " + e.getMessage());
            model.addAttribute("errors", errors);
            model.addAttribute("courseId", courseId);
            model.addAttribute("course", courseService.findCourseById(courseId));
            model.addAttribute("exam", examRequest);
            return "instructor_course_management";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = courseService.getInstructorByEmail(email);
        if (instructor == null) {
            logger.error("Instructor not found for email: {}", email);
            return "redirect:/instructor/login";
        }
        model.addAttribute("courses", courseService.getCoursesByInstructor(instructor));
        return "instructor_dashboard";
    }
}