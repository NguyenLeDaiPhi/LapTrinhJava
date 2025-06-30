package com.jcertpre.service;

import com.jcertpre.model.Learner;
import com.jcertpre.repository.LearnerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (learner.getRole() != null && !learner.getRole().isBlank()) {
            // Spring Security expects roles to start with "ROLE_"
            authorities.add(new SimpleGrantedAuthority("ROLE_" + learner.getRole().toUpperCase()));
        }
        return new User(learner.getEmail(), learner.getPassword(), authorities);
    }
}