package com.jcertpre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcertpre.model.ExamSimulation;
import com.jcertpre.repository.ExamSimulationRepository;

@Service
public class ExamService {
    
    @Autowired
    private ExamSimulationRepository examSimulationRepository;

    public void saveExam(ExamSimulation exam) {
        examSimulationRepository.save(exam);
    }

    public ExamSimulation findByCourseId(Long courseId) {
        return examSimulationRepository.findById(courseId).orElse(null);
    }
}
