package com.jcertpre.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jcertpre.dto.RecommendationForm;
import com.jcertpre.dto.StudyPlanItem;
import com.jcertpre.model.JapaneseLevel;
import com.jcertpre.model.Learner;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Override
    public List<StudyPlanItem> generatePlan(Learner learner, RecommendationForm form) {
        JapaneseLevel current = form.getCurrentLevel();
        JapaneseLevel target = form.getTargetLevel();
        if (current.ordinal() > target.ordinal()) {
            throw new IllegalArgumentException("Mức hiện tại không được cao hơn mức mục tiêu.");
        }
        List<StudyPlanItem> plan = new ArrayList<>();
        if (current == target) {
            plan.add(new StudyPlanItem(
                    "Ôn tập củng cố ở mức " + current.getDisplayName(),
                    "Bạn đã ở mức " + current.getDisplayName() + ". Gợi ý ôn các kỹ năng yếu, luyện đề.",
                    4,
                    "Tài nguyên ôn tập cho " + current.getDisplayName()
            ));
            return plan;
        }
        int weeksPerLevel = 8;
        JapaneseLevel[] levels = JapaneseLevel.values();
        for (int i = current.ordinal(); i < target.ordinal(); i++) {
            JapaneseLevel from = levels[i];
            JapaneseLevel to = levels[i+1];
            String title = String.format("Từ %s đến %s", from.getDisplayName(), to.getDisplayName());
            String desc = String.format("Học kiến thức để nâng từ %s lên %s: ngữ pháp, từ vựng, luyện nghe/đọc/viết/nói.", from.getDisplayName(), to.getDisplayName());
            String resources = getResourcesForLevel(to);
            plan.add(new StudyPlanItem(title, desc, weeksPerLevel, resources));
        }
        plan.add(new StudyPlanItem(
                "Tổng kết & luyện đề " + target.getDisplayName(),
                "Luyện đề chính thức, củng cố kỹ năng để sẵn sàng đạt trình độ " + target.getDisplayName(),
                4,
                "Bộ đề JLPT " + target.getDisplayName() + " và tài nguyên liên quan."
        ));
        return plan;
    }

    private String getResourcesForLevel(JapaneseLevel level) {
        switch(level) {
            case N5: return "Minna no Nihongo, flashcard N5...";
            case N4: return "Shin Kanzen Master N4, luyện nghe N4...";
            case N3: return "Shin Kanzen Master N3, TRY! N3...";
            case N2: return "Shin Kanzen Master N2, Sou Matome N2...";
            case N1: return "Shin Kanzen Master N1, Sou Matome N1...";
            case NEWBIE: return "Học Hiragana/Katakana...";
            default: return "";
        }
    }
}
