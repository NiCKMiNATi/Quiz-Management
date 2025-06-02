package com.example.quizapp.service;

import com.example.quizapp.model.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    User registerUser(User user);
    Optional<User> findByEmail(String email);

}
