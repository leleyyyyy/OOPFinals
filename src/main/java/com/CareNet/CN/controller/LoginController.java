package com.CareNet.CN.controller;

import com.CareNet.CN.model.Role;
import com.CareNet.CN.model.User;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.authenticate(username, password);

        if (user != null) {
            // Redirect based on user role
            if (user.getRole() == Role.DOCTOR) {
                return "redirect:/doctor/home"; // Doctor's homepage
            } else if (user.getRole() == Role.PATIENT) {
                return "redirect:/patient/home"; // Patient's homepage
            }
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login"; // Return to login page with error
        }
        return username;
    }
}
