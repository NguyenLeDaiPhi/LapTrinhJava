package com.jcertpre.controller;

import com.jcertpre.dto.ExamGradeDTO;
import com.jcertpre.dto.ExamSubmissionRequest;
import com.jcertpre.model.Course;
import com.jcertpre.model.Question;
import com.jcertpre.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/learner")
public class LearnerExamController {

    private static final Logger logger = LoggerFactory.getLogger(LearnerExamController.class);

    @Autowired
    private CourseService courseService;

    /**
     * Displays the exam simulation for a specific course to the learner.
     *
     * @param courseId The ID of the course.
     * @param model The Spring Model to pass data to the view.
     * @return The name of the Thymeleaf template for the exam page.
     */
    @GetMapping("/course/{courseId}/exam")
    public String showExamSimulation(@PathVariable Long courseId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.findCourseById(courseId);
            if (course.getExamSimulation() == null || course.getExamSimulation().getQuestions().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No exam available for this course yet.");
                return "redirect:/learner/course/" + courseId; // Redirect back to course details
            }

            List<Question> questions = courseService.getExamQuestionsForCourse(courseId);
            model.addAttribute("course", course);
            model.addAttribute("questions", questions);
            model.addAttribute("examSubmission", new ExamSubmissionRequest()); // For form binding
            return "learner_exam_simulation"; // Create this Thymeleaf template
        } catch (RuntimeException e) {
            logger.error("Error showing exam for course {}: {}", courseId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error loading exam: " + e.getMessage());
            return "redirect:/learner/course/" + courseId;
        }
    }

    /**
     * Handles the submission of a learner's exam answers.
     *
     * @param courseId The ID of the course.
     * @param submission The DTO containing the learner's answers.
     * @param model The Spring Model to pass data to the view.
     * @return The results page.
     */
    @PostMapping("/course/{courseId}/exam")
    public String submitExam(@PathVariable("courseId") Long courseId,
                             @ModelAttribute("examSubmission") ExamSubmissionRequest submission,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("error", "You must be logged in to submit an exam.");
                return "redirect:/login";
            }
            String learnerEmail = authentication.getName();

            ExamGradeDTO gradeResult = courseService.gradeExam(courseId, learnerEmail, submission);

            model.addAttribute("score", gradeResult.getScore());
            model.addAttribute("totalQuestions", gradeResult.getTotalQuestions());
            model.addAttribute("correctAnswers", gradeResult.getCorrectAnswers());
            model.addAttribute("questionResults", gradeResult.getQuestionResults());
            model.addAttribute("submittedAnswers", submission.getAnswers());
            model.addAttribute("questions", courseService.getExamQuestionsForCourse(courseId));
            model.addAttribute("course", courseService.findCourseById(courseId));
            return "learner_exam_results";
        } catch (RuntimeException e) {
            logger.error("Error submitting exam for course {}: {}", courseId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error submitting exam: " + e.getMessage());
            return "redirect:/learner/course/" + courseId + "/exam";
        }
    }
}