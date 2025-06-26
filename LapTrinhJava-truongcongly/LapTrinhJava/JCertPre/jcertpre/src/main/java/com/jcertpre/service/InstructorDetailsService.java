package com.jcertpre.service;

import com.jcertpre.model.Instructor;
import com.jcertpre.repository.InstructorRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class InstructorDetailsService implements UserDetailsService {
    private InstructorRepository instructorRepository;

    public InstructorDetailsService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Instructor instructor = instructorRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Instructor not found with email: " + email));
        return new User(instructor.getEmail(), instructor.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_INSTRUCTOR")));
    }
}