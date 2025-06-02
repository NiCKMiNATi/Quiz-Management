package com.example.quizapp.security;

import com.example.quizapp.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity, consider enabling in production
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/register", "/login", "/css/**").permitAll() // Allow public access to registration and login
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")// Custom login page
                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard after successful login
                .permitAll() // Allow everyone to access the login page
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                    .permitAll()
            ); // Allow everyone to log out

        return http.build();
    }
}
