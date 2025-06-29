package com.jcertpre.service;

import com.jcertpre.dto.RegisterRequest;
import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnerService implements ILearnerService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký tài khoản mới
    public String registerLearner(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }
        if (!request.isAgreeTerms()) {
            return "You must agree to the terms of service";
        }
        if (learnerRepository.existsByEmail(request.getEmail())) {
            return "Email already in use";
        }

        Learner learner = new Learner();
        learner.setName(request.getName());
        learner.setEmail(request.getEmail());
        learner.setPassword(passwordEncoder.encode(request.getPassword()));
        learner.setRole("USER");  // Thiết lập vai trò mặc định
        learner.setPhone("");
        learner.setAddress("");

        learnerRepository.save(learner);
        return null;
    }

    // Dùng cho login hoặc kiểm tra
    public Learner findByEmail(String email) {
        return learnerRepository.findByEmail(email);
    }

    // Cập nhật thông tin cá nhân
    public Learner updateProfile(String email, String name, String phone, String address) {
        Learner learner = learnerRepository.findByEmail(email);
        if (learner == null) {
            throw new IllegalArgumentException("Learner not found with email: " + email);
        }

        learner.setName(name);
        learner.setPhone(phone);
        learner.setAddress(address);

        return learnerRepository.save(learner);
    }

    // Dùng cho ADD hoặc EDIT learner (qua admin)
    @Override
    public Learner saveLearner(Learner learner) {
        // Nếu là mật khẩu thuần, cần mã hoá
        if (learner.getPassword() != null && !learner.getPassword().startsWith("{bcrypt}")) {
            learner.setPassword(passwordEncoder.encode(learner.getPassword()));
        }

        return learnerRepository.save(learner);
    }

    // Xem danh sách
    @Override
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    // Lấy theo ID
    @Override
    public Learner getLearnerById(Long id) {
        return learnerRepository.findById(id).orElse(null);
    }

    // Xoá
    @Override
    public void deleteLearner(Long id) {
        learnerRepository.deleteById(id);
    }
}
