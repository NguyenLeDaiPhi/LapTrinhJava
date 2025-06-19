package com.jcertpre.service;

import com.jcertpre.dto.SubmitExamDTO;
import com.jcertpre.model.*;
import com.jcertpre.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamService {

    @Autowired
    private ExamQuestionRepository questionRepo;

    @Autowired
    private ExamResultRepository resultRepo;

    @Autowired
    private ExamAnswerDetailRepository detailRepo;

    public List<ExamQuestion> getRandomQuestions() {
        // Lấy 5 câu hỏi ngẫu nhiên (demo)
        List<ExamQuestion> all = questionRepo.findAll();
        Collections.shuffle(all);
        return all.subList(0, Math.min(5, all.size()));
    }

    public ExamResult evaluateExam(SubmitExamDTO dto) {
        int total = dto.getAnswers().size();
        int correct = 0;
        List<ExamAnswerDetail> details = new ArrayList<>();

        for (Map.Entry<Long, String> entry : dto.getAnswers().entrySet()) {
            Long qId = entry.getKey();
            String selected = entry.getValue();
            ExamQuestion question = questionRepo.findById(qId).orElse(null);
            if (question == null) continue;

            boolean isCorrect = selected.equalsIgnoreCase(question.getCorrectAnswer());
            if (isCorrect) correct++;

            ExamAnswerDetail detail = new ExamAnswerDetail();
            detail.setQuestion(question);
            detail.setCorrectAnswer(question.getCorrectAnswer());
            detail.setUserAnswer(selected);
            detail.setExplanation(question.getExplanation());
            details.add(detail);
        }

        ExamResult result = new ExamResult();
        result.setTotalQuestions(total);
        result.setCorrectAnswers(correct);
        result.setScore((int)((correct * 100.0) / total));
        result.setAnswerDetails(details);
        resultRepo.save(result);

        for (ExamAnswerDetail d : details) {
            d.setExamResult(result);
            detailRepo.save(d);
        }

        return result;
    }

    public ExamResult getExamResultById(Long id) {
        return resultRepo.findById(id).orElse(null);
    }
}
