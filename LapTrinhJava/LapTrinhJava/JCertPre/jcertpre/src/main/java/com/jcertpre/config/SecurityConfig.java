package com.jcertpre.config;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.InstructorRepository;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LearnerRepository learnerRepository;
    private final InstructorRepository instructorRepository;

    public SecurityConfig(LearnerRepository learnerRepository, InstructorRepository instructorRepository) {
        this.learnerRepository = learnerRepository;
        this.instructorRepository = instructorRepository;
    }

    @Bean
    @Order(0) // Highest priority for admin security
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/admin/**", "/admin-dashboard") // Apply this rule to all admin URLs
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().hasRole("ADMIN") // Require the user to have the ADMIN role
            )
            .formLogin(form -> form
                .loginPage("/login") // Redirect to the main login page if not authenticated
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .exceptionHandling(e -> e.accessDeniedPage("/access-denied")) // Show access denied page if role is wrong
            .csrf(AbstractHttpConfigurer::disable); // For simplicity, can be configured more securely
        return http.build();
    }

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
                .requestMatchers("/", "/register", "/login", "/registration-success", "/access-denied", "/courses", "/css/**", "/assets/**", "/js/**", "/lib/**", "/img/**").permitAll()
                .requestMatchers("/profile", "/learner/**").hasRole("USER") // Role should be USER to match database
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
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var learner = learnerRepository.findByEmail(username);
            if (learner.isPresent()) {
                Learner l = learner.get();
                List<org.springframework.security.core.authority.SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (l.getRole() != null && !l.getRole().isBlank()) {
                    // This correctly assigns ADMIN or USER roles from the database
                    authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + l.getRole().toUpperCase()));
                } else {
                    // Default to USER if no role is set
                    authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"));
                }
                return new org.springframework.security.core.userdetails.User(l.getEmail(), l.getPassword(), authorities);
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
