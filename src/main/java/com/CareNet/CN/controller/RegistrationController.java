package com.CareNet.CN.controller;

import com.CareNet.CN.model.Role;
import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    // Redirect root URL to the registration page
    @GetMapping("/")
    public String redirectToRegistration() {
        return "redirect:/register"; // Redirect to the registration page
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Create a new User object
        return "registerpage"; // Return the registration page
    }

    @PostMapping("/register")
    public String registerUser (@ModelAttribute User user) {
        // Check if the username or email already exists
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return "redirect:/register?error=true"; // Redirect to registration page with error
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the default role for new users (e.g., PATIENT)
        user.setRole(Role.PATIENT); // Set the role to PATIENT or DOCTOR as needed

        userRepository.save(user); // Save the user to the database
        return "redirect:/login"; // Redirect to login page after successful registration
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the login page
    }
}