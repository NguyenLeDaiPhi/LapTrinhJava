package com.jcertpre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcertpre.dto.SubmitExamDTO;
import com.jcertpre.model.ExamQuestion;
import com.jcertpre.model.ExamResult;
import com.jcertpre.service.ExamService;

@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping({"", "/", "/index"})
    public String showExamIndex() {
        return "exam/index"; // src/main/resources/templates/exam/index.html
    }

    @GetMapping("/simulation")
    public String startExam(Model model) {
        List<ExamQuestion> questions = examService.getRandomQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("submitExamDTO", new SubmitExamDTO());
        return "exam/simulation"; // src/main/resources/templates/exam/simulation.html
    }

    @GetMapping("/questions")
    public String showQuestions(Model model) {
        List<ExamQuestion> questions = examService.getRandomQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("submitExamDTO", new SubmitExamDTO());
        return "exam/simulation"; // src/main/resources/templates/exam/simulation.html
    }       

    @GetMapping("/confirm/{id}")
    public String confirmExam(@PathVariable Long id, Model model) {
        ExamResult result = examService.getExamResultById(id);
        model.addAttribute("totalQuestions", result.getTotalQuestions());
        model.addAttribute("correctAnswers", result.getCorrectAnswers());
        model.addAttribute("score", result.getScore());
        model.addAttribute("answerDetails", result.getAnswerDetails()); // danh sách câu + đáp án
        return "exam/confirm"; // src/main/resources/templates/exam/confirm.html
    }           
    @GetMapping("/result/{id}")
    public String viewResult(@PathVariable Long id, Model model) {
        ExamResult result = examService.getExamResultById(id);
        model.addAttribute("totalQuestions", result.getTotalQuestions());
        model.addAttribute("correctAnswers", result.getCorrectAnswers());
        model.addAttribute("score", result.getScore());
        model.addAttribute("answerDetails", result.getAnswerDetails()); // danh sách câu + đáp án
        return "exam/result"; // src/main/resources/templates/exam/result.html
    }

    @RequestMapping("/")
    public String redirectToIndex() {
        return "redirect:/exam/index";
    }
}
