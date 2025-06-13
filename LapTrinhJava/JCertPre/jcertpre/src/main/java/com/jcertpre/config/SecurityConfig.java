package com.jcertpre.config;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.InstructorRepository;
import com.jcertpre.repository.LearnerRepository;
import com.jcertpre.service.InstructorDetailsService;
import com.jcertpre.service.LearnerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private LearnerDetailsService learnerDetailsService;

    @Autowired
    private InstructorDetailsService instructorDetailsService;

    @Bean
    @Order(1)
    public SecurityFilterChain instructorSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/instructor/**") // Add this!
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/instructor/register", "/instructor/login").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form
                .loginPage("/instructor/login")
                .loginProcessingUrl("/instructor/login")
                .defaultSuccessUrl("/instructor/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/instructor/logout")
                .logoutSuccessUrl("/instructor/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain learnerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**") // Catch everything else
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/register", "/login", "/registration-success").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
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
        authenticationManagerBuilder
            .userDetailsService(instructorDetailsService)
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
            instructorRepository.findAll().forEach(instructor -> {
                String password = instructor.getPassword();
                // Check if password is not already hashed with BCrypt
                if (!password.startsWith("$2a$") && !password.startsWith("{bcrypt}")) {
                    System.out.println("Hashing password for instructor: " + instructor.getEmail());
                    instructor.setPassword(passwordEncoder.encode(password));
                    instructorRepository.save(instructor);
                }
            });
        };
    }
}