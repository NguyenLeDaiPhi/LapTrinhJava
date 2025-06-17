package com.jcertpre.config;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Bean
    @Order(1)
    public SecurityFilterChain instructorSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/instructor/**")
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/instructor/register", "/instructor/login", "/css/**").permitAll()
                .requestMatchers("/instructor/**").hasRole("INSTRUCTOR")
            )
            .formLogin(form -> form
                .loginPage("/instructor/login")
                .loginProcessingUrl("/instructor/login")
                .defaultSuccessUrl("/instructor/index", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/instructor/logout")
                .logoutSuccessUrl("/instructor/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/instructor/**") // Temporary for testing
            );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain learnerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**")
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/register", "/login", "/registration-success", "/access-denied", "/courses").permitAll()
                .requestMatchers("/profile").hasRole("LEARNER")
                .anyRequest().authenticated()
            )
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
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var learner = learnerRepository.findByEmail(username);
            if (learner.isPresent()) {
                return new org.springframework.security.core.userdetails.User(
                    learner.get().getEmail(),
                    learner.get().getPassword(),
                    java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_LEARNER"))
                );
            }
            var instructor = instructorRepository.findByEmail(username);
            if (instructor.isPresent()) {
                return new org.springframework.security.core.userdetails.User(
                    instructor.get().getEmail(),
                    instructor.get().getPassword(),
                    java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_INSTRUCTOR"))
                );
            }
            throw new UsernameNotFoundException("User not found: " + username);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService())
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
                if (!password.startsWith("$2a$") && !password.startsWith("{bcrypt}")) {
                    System.out.println("Hashing password for user: " + learner.getEmail());
                    learner.setPassword(passwordEncoder.encode(password));
                    learnerRepository.save(learner);
                }
            });
            instructorRepository.findAll().forEach(instructor -> {
                String password = instructor.getPassword();
                if (!password.startsWith("$2a$") && !password.startsWith("{bcrypt}")) {
                    System.out.println("Hashing password for instructor: " + instructor.getEmail());
                    instructor.setPassword(passwordEncoder.encode(password));
                    instructorRepository.save(instructor);
                }
            });
        };
    }
}