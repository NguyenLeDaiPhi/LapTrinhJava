package com.jcertpre.service;

import java.util.List;

import com.jcertpre.dto.RecommendationForm;
import com.jcertpre.dto.StudyPlanItem;
import com.jcertpre.model.Learner;

public interface RecommendationService {
    /**
     * Tạo danh sách StudyPlanItem dựa trên learner và form.
     * Nếu bạn không cần dynamic, có thể để method trống hoặc không dùng service.
     */
    List<StudyPlanItem> generatePlan(Learner learner, RecommendationForm form);
}
