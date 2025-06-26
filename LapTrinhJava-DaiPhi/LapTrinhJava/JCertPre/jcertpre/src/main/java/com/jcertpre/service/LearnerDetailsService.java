package com.jcertpre.service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LearnerDetailsService implements UserDetailsService {

    private LearnerRepository learnerRepository;

    public LearnerDetailsService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Learner learner = learnerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Learner not found with email: " + email));
        return new User(learner.getEmail(), learner.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_LEARNER")));
    }
}