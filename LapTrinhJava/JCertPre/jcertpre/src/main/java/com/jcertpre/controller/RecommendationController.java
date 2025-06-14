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
            // 1. Thêm levels để render lại dropdown khi có lỗi
            model.addAttribute("levels", JapaneseLevel.values());

            // 2. Kiểm validation annotation cơ bản (@NotBlank, @Email, @NotNull)
            if (bindingResult.hasErrors()) {
                // Nếu lỗi annotation: fullName hoặc email => bước 1; lỗi targetLevel hoặc currentLevel annotation => bước 2
                if (bindingResult.hasFieldErrors("fullName") || bindingResult.hasFieldErrors("email")) {
                    model.addAttribute("step", 1);
                } else {
                    model.addAttribute("step", 2);
                }
                return "recommendation-form";
            }

            // 3. Kiểm email tồn tại trong DB
            String emailTrim = form.getEmail().trim().toLowerCase();
            Learner learner = learnerRepository.findByEmail(emailTrim);
            if (learner == null) {
                bindingResult.rejectValue("email", "notExist", "Email chưa đăng ký hoặc không phải thành viên");
                model.addAttribute("step", 1);
                return "recommendation-form";
            }

            // 4. Kiểm họ tên khớp
            String formName = form.getFullName().trim();
            String learnerName = learner.getName(); // hoặc getFullName()
            if (learnerName == null || !learnerName.equalsIgnoreCase(formName)) {
                bindingResult.rejectValue("fullName", "mismatch", "Họ tên không khớp với tài khoản");
                model.addAttribute("step", 1);
                return "recommendation-form";
            }

            // 5. (Nếu bạn vẫn muốn) Kiểm mức current <= target
            JapaneseLevel current = form.getCurrentLevel();
            JapaneseLevel target = form.getTargetLevel();
            if (current != null && target != null) {
                if (current.ordinal() > target.ordinal()) {
                    bindingResult.rejectValue("targetLevel", "levelMismatch", "Mức mục tiêu phải >= mức hiện tại");
                    model.addAttribute("step", 2);
                    return "recommendation-form";
                }
            }
            // Nếu bạn không muốn kiểm dòng này, bạn có thể comment hoặc bỏ đoạn trên

            // 6. Kiểm bindingResult một lần nữa sau rejectValue
            if (bindingResult.hasErrors()) {
                return "recommendation-form";
            }

            // 7. Chuẩn bị model cho view kết quả (nếu template cần hiển thông tin)
            model.addAttribute("learnerName", learnerName);
            model.addAttribute("currentLevel", current != null ? current.getDisplayName() : "");
            model.addAttribute("targetLevel", target.name());
            List<StudyPlanItem> planItems = recommendationService.generatePlan(learner, form);
            model.addAttribute("planItems", planItems);

            // 8. Chọn view kết quả chỉ dựa vào targetLevel
            String levelPart;
            if (target == JapaneseLevel.NEWBIE) {
                // Map “Người mới” về N5 nếu cần
                levelPart = "n5";
            } else {
                // Ví dụ target.name() = "N2" → "n2"
                levelPart = target.name().toLowerCase();
            }
            String viewName = "createPlan_" + levelPart; // Ví dụ "createPlan_n2"
            return viewName;
        }

}
