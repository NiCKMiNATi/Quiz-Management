package com.example.quizapp.service;

import com.example.quizapp.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.quizapp.model.User user = userRepository.findByEmail(email);

        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // Assuming Role is an enum with a name() method
                .build();
    }
}
