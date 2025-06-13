package com.jcertpre.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcertpre.dto.RecommendationForm;
import com.jcertpre.dto.StudyPlanItem;
import com.jcertpre.model.JapaneseLevel;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import com.jcertpre.service.RecommendationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/recommendation")
public class RecommendationController {

    private final LearnerRepository learnerRepository;
    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(LearnerRepository learnerRepository,
                                    RecommendationService recommendationService) {
        this.learnerRepository = learnerRepository;
        this.recommendationService = recommendationService;
    }

    /**
     * GET hiển thị form wizard
     */
    @GetMapping
    public String showForm(Model model) {
        // Nếu lần đầu chưa có recommendationForm trong model, tạo mới
        if (!model.containsAttribute("recommendationForm")) {
            model.addAttribute("recommendationForm", new RecommendationForm());
        }
        // Danh sách levels cho dropdown
        model.addAttribute("levels", JapaneseLevel.values());
        // Khởi bước wizard: step = 1
        model.addAttribute("step", 1);
        return "recommendation-form";
    }

    /**
     * Endpoint AJAX: kiểm tra email tồn tại?
     * GET /recommendation/check-email?email=...
     * Trả JSON: {"exists": true/false}
     */
    @GetMapping("/check-email")
    @ResponseBody
    public Map<String, Boolean> checkEmailExists(@RequestParam("email") String email) {
        boolean exists = false;
        if (email != null && !email.isBlank()) {
            String e = email.trim().toLowerCase();
            // Giả sử repository trả null nếu không tìm, hoặc bạn có existsByEmail:
            // exists = learnerRepository.existsByEmail(e);
            Learner learner = learnerRepository.findByEmail(e);
            exists = (learner != null);
        }
        return Collections.singletonMap("exists", exists);
    }

    /**
     * POST xử lý submit cuối.
     * Kiểm @Valid và custom validation: email tồn tại, fullName khớp, levels logic.
     * Khi có lỗi, set model.addAttribute("step", ...) để template JS khởi đúng bước.
     */
    @PostMapping
    public String handleForm(
            @Valid @ModelAttribute("recommendationForm") RecommendationForm form,
            BindingResult bindingResult,
            Model model
    ) {
        // Luôn thêm levels để hiển lại dropdown khi render form
        model.addAttribute("levels", JapaneseLevel.values());

        // 1. Kiểm validation annotation cơ bản (@NotBlank, @Email, @NotNull)
        if (bindingResult.hasErrors()) {
            // Nếu lỗi annotation, chúng ta chưa biết lỗi thuộc bước nào:
            // fullName hoặc email lỗi: bước 1; currentLevel/targetLevel lỗi: bước 2.
            // Ta kiểm bindingResult để đặt step:
            if (bindingResult.hasFieldErrors("fullName") || bindingResult.hasFieldErrors("email")) {
                model.addAttribute("step", 1);
            } else {
                // nếu lỗi ở currentLevel hoặc targetLevel:
                model.addAttribute("step", 2);
            }
            return "recommendation-form";
        }

        // 2. Kiểm email tồn tại
        String emailTrim = form.getEmail().trim().toLowerCase();
        Learner learner = learnerRepository.findByEmail(emailTrim);
        if (learner == null) {
            bindingResult.rejectValue("email", "notExist", "Email chưa đăng ký hoặc không phải thành viên");
            model.addAttribute("step", 1);
            return "recommendation-form";
        }

        // 3. Kiểm họ tên khớp với Learner in DB
        String formName = form.getFullName().trim();
        // Giả sử Learner có getter getName() hoặc getFullName()
        String learnerName = learner.getName(); // hoặc learner.getFullName()
        if (learnerName == null || !learnerName.equalsIgnoreCase(formName)) {
            bindingResult.rejectValue("fullName", "mismatch", "Họ tên không khớp với tài khoản");
            model.addAttribute("step", 1);
            return "recommendation-form";
        }

        // 4. Kiểm mức current <= target
        JapaneseLevel current = form.getCurrentLevel();
        JapaneseLevel target = form.getTargetLevel();
        if (current != null && target != null) {
            if (current.ordinal() > target.ordinal()) {
                // Lỗi trên targetLevel
                bindingResult.rejectValue("targetLevel", "levelMismatch", "Mức mục tiêu phải >= mức hiện tại");
                model.addAttribute("step", 2);
                return "recommendation-form";
            }
        }

        // 5. Nếu có lỗi bindingResult do rejectValue thêm ở trên, xử lý return
        if (bindingResult.hasErrors()) {
            // step đã được set ở trên
            return "recommendation-form";
        }

        // 6. Nếu hợp lệ: chuẩn bị dữ liệu cho view kết quả
        model.addAttribute("learnerName", learnerName);
        model.addAttribute("currentLevel", current.getDisplayName());
        model.addAttribute("targetLevel", target.name());

        // 7. Gọi service để sinh plan
        List<StudyPlanItem> planItems = recommendationService.generatePlan(learner, form);
        model.addAttribute("planItems", planItems);

        // 8. Chọn view kết quả dựa trên targetLevel
        String viewName = "createPlan_" + target.name();
        // Nếu bạn có enum NEWBIE hoặc NGUOI_MOI, điều chỉnh về template phù hợp
        if (target == JapaneseLevel.NEWBIE) {
            // Giả sử bạn muốn map NGUOI_MOI về template N5
            viewName = "createPlan_N5";
        }
        return viewName;
    }
}
