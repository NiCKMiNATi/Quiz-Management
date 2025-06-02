package com.example.quizapp.controller;

import com.example.quizapp.model.User;
import com.example.quizapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Assuming User is a model class for user registration
        return "register"; // This should return the name of the registration view template
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if the user already exists
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "User with this email already exists.");
            return "register"; // Return to registration form with error message
        }

        // Save the new user
        userService.registerUser(user);
        return "redirect:/login"; // Redirect to login page after successful registration
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // This should return the name of the login view template
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        model.addAttribute("email", user.getEmail());
        model.addAttribute("fullName", user.getFullName());
        return "dashboard"; // This should return the name of the dashboard view template
    }
}
