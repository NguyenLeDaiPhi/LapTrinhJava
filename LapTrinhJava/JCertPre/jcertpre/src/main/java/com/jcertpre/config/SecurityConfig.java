package com.jcertpre.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jcertpre.repository.LearnerRepository;
import com.jcertpre.service.LearnerDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LearnerDetailsService learnerDetailsService;

    @Autowired
    private LearnerRepository learnerRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép truy cập endpoint check-email AJAX
                .requestMatchers("/recommendation/check-email").permitAll()

                // Cho phép truy cập trang tạo kế hoạch và tài nguyên tĩnh mà không cần đăng nhập
                .requestMatchers(
                    "/createPlan",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/register",
                    "/login",
                    "/registration-success",
                    "/recommendation",
                    "/recommendation/**"
                ).permitAll()

                // Các request còn lại yêu cầu auth
                .anyRequest().authenticated()
            )
            // Nếu bạn disable CSRF hoàn toàn, AJAX GET không cần token. Giữ như cũ:
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/index", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(learnerDetailsService)
            .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner hashExistingPasswords(PasswordEncoder passwordEncoder) {
        return args -> {
            learnerRepository.findAll().forEach(learner -> {
                String password = learner.getPassword();
                // Check if password is not already hashed with BCrypt
                if (!password.startsWith("$2a$") && !password.startsWith("{bcrypt}")) {
                    System.out.println("Hashing password for user: " + learner.getEmail());
                    learner.setPassword(passwordEncoder.encode(password));
                    learnerRepository.save(learner);
                }
            });
        };
    }
}
