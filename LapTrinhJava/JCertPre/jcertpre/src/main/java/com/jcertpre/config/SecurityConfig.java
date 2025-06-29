package com.jcertpre.config;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import com.jcertpre.service.LearnerDetailsService;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private LearnerDetailsService learnerDetailsService;

    @Autowired
    private LearnerRepository learnerRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
    .requestMatchers("/register", "/register/**", "/registration-success", "/login", "/css/**", "/js/**", "/images/**", "/webjars/**")
    .permitAll()
    .anyRequest().authenticated()
)

            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customLoginSuccessHandler)
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
