package com.CareNet.CN.controller;

import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SiteController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/register"; // Redirects to the root URL
    }

    @GetMapping("/register")
    public String showSignpp(Model model){
        model.addAttribute("user", new User());
        return "registerpage";
    }
    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new User());
        return "login";

    }

    @PostMapping("/process_register")
    public String processRegistration(@ModelAttribute User user, Model model) {
        // Check if username or email already exists
        if (repo.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Username is already taken.");
            return "registerpage"; // Replace with your actual form view name
        }

        if (repo.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email is already registered.");
            return "registerpage";
        }

        user.setRole("patient");
        // Encode the password and save
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return "login";
    }

    @PostMapping("/process_login")
    public String processLogin(@ModelAttribute User user, Model model) {
        // Find the user by username
        User existingUser = repo.findByUsername(user.getUsername());

        // Check if the user exists and if the password matches
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // Successful login
            if("patient".equalsIgnoreCase(existingUser.getRole())){
                return "patientHome"; // Redirect to home page or dashboard
            } else {
                return "doctorHome";
            }
        } else {
            // Failed login
            model.addAttribute("error", "Invalid email or password.");
            return "login"; // Return to login form with error
        }
    }


}

