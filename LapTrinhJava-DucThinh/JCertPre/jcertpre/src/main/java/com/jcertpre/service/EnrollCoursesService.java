package com.jcertpre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcertpre.repository.EnrollCoursesRepository;

@Service
public class EnrollCoursesService {

    @Autowired
    private EnrollCoursesRepository enrollCoursesRepository;

    public boolean enrollCoursesLearnerToCourses(Long learnerId, Long courseId) {
      
        if (enrollCoursesRepository.existsByLearnerIdAndCourseId(learnerId, courseId)) { 
            return false; 
        }
        enrollCoursesRepository.saveEnrollment(learnerId, courseId); 
        return true;
    }
}