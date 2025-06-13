package com.jcertpre.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jcertpre.model.Instructor;
import com.jcertpre.repository.InstructorRepository;

@Service
public class InstructorDetailsService implements UserDetailsService {
    
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Instructor instructor = instructorRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Instructor not found with email: " + username));
        
        return new User(
            instructor.getEmail(),
            instructor.getPassword(),
            Collections.emptyList()
        );
    }
}
