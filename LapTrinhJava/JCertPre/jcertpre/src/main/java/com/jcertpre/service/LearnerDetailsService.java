package com.jcertpre.service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LearnerDetailsService implements UserDetailsService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Learner learner = learnerRepository.findByEmail(email);
        if (learner == null) {
            throw new UsernameNotFoundException("Learner not found with email: " + email);
        }
        return new User(learner.getEmail(), learner.getPassword(), new ArrayList<>());
    }
}
