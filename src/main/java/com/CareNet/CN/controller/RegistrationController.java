package com.CareNet.CN.controller;

import com.CareNet.CN.model.User;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService; // Declare and inject userService

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Create a new User object
        return "registerpage"; // Return the registration page
    }

    @PostMapping("/register") // Ensure this is the correct mapping for registration
    public String registerUser (@ModelAttribute User user, BindingResult result) {
        String registrationResult = userService.registerUser (user, result);
        if ("success".equals(registrationResult)) {
            return "redirect:/login"; // Redirect to login page after successful registration
        }
        return "registerpage"; // Return to registration page if there are errors
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the login page
    }
}