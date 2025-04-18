package com.CareNet.CN.controller;

import com.CareNet.CN.model.Role;
import com.CareNet.CN.model.User;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService; // Declare and inject userService

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Create a new User object
        return "registerpage"; // Return the registration page
    }

    @PostMapping("/Login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.authenticateUser(username, password);

        if (user != null) {
            // Use getRoleId() to get the ordinal value
            if (user.getRoleId() == Role.DOCTOR.getId()) {
                return "redirect:/doctorHome"; // Example for doctor redirection
            } else if (user.getRoleId() == Role.PATIENT.getId()) {
                return "redirect:/patientHome"; // Example for patient redirection
            }
        }

        model.addAttribute("error", "Invalid credentials"); // Add an error attribute
        return "Login"; // Return login view
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "Login"; // Return the login page
    }
}