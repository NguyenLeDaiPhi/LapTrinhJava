package com.example.jcertpre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JCert Pre Application - Ứng dụng quản lý giảng viên và khóa học
 * 
 * Chức năng chính:
 * - Quản lý thông tin giảng viên (Instructor)
 * - Quản lý khóa học (Course) 
 * - Quản lý lịch dạy (Schedule)
 * - Quản lý câu hỏi học viên (Question)
 * - Xác thực và phân quyền với JWT
 * - Giao diện web với Thymeleaf templates
 * 
 * @author JCert Team
 * @version 1.0
 */
@SpringBootApplication
@EnableScheduling // Kích hoạt scheduling cho các tác vụ định kỳ
@EnableTransactionManagement // Kích hoạt quản lý transaction
public class JCertPreApplication {

    /**
     * Main method để khởi chạy Spring Boot application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Thiết lập một số system properties nếu cần
        System.setProperty("spring.devtools.restart.enabled", "true");
        System.setProperty("spring.devtools.livereload.enabled", "true");
        
        // Khởi chạy ứng dụng Spring Boot
        SpringApplication.run(JCertPreApplication.class, args);
        
        // Log thông báo khởi chạy thành công
        System.out.println("=".repeat(60));
        System.out.println("🚀 JCert Pre Application đã khởi chạy thành công!");
        System.out.println("📚 Hệ thống quản lý giảng viên và khóa học");
        System.out.println("🌐 Truy cập: http://localhost:8080");
        System.out.println("📖 API Docs: http://localhost:8080/swagger-ui.html");
        System.out.println("=".repeat(60));
    }
}