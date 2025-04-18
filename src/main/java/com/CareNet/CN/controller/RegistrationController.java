package com.CareNet.CN.controller;

import com.CareNet.CN.model.User;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

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
    public String registerUser (User user, BindingResult result, Model model) {
        String registrationResult = userService.registerUser (user, result); // Call the service method

        if (result.hasErrors()) {
            // If there are errors, return to the registration page with the user object
            model.addAttribute("user", user);
            return "registerpage"; // Return to the registration page
        }

        // Redirect to the login page after successful registration
        return "redirect:/login"; // Redirect to the login page after registration
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the login page
    }
}