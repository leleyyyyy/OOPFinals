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
public class LoginController {

    @Autowired
    private UserService userService; // Inject userService

    @PostMapping("/login") // Ensure this is the correct mapping for login
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.authenticateUser (username, password);

        if (user != null) {
            // Redirect based on user role
            if (user.getRole() == Role.DOCTOR) {
                return "redirect:/doctor/doctorHome"; // Example for doctor redirection
            } else if (user.getRole() == Role.PATIENT) {
                return "redirect:/patient/patientHome"; // Example for patient redirection
            }
        }

        model.addAttribute("error", "Invalid credentials"); // Add an error attribute
        return "login"; // Return login view
    }
}