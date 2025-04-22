package com.CareNet.CN.controller;

import com.CareNet.CN.model.PatientAssessment; // Import the PatientAssessment model
import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.PatientAssessmentRepository; // Import the PatientAssessment repository
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
    private PatientAssessmentRepository assessmentRepo; // Add the PatientAssessment repository

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/register"; // Redirects to the root URL
    }

    @GetMapping("/register")
    public String showSignUp(Model model) {
        model.addAttribute("user", new User());
        return "registerpage";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model) {
        // You can add any necessary attributes to the model here
        return "editPage"; // Return the name of the new Thymeleaf template
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

    // New method to show the add assessment page
    @GetMapping("/addAssessment")
    public String showAddAssessmentPage(Model model) {
        model.addAttribute("assessment", new PatientAssessment()); // Create a new PatientAssessment object
        return "addAssessment"; // Return the name of the Thymeleaf template
    }

    // New method to process the add assessment form submission
    @PostMapping("/process_addAssessment")
    public String processAddAssessment(@ModelAttribute PatientAssessment assessment, Model model) {
        // Find the user based on the patient ID
        User user = repo.findById(assessment.getUser ().getId()).orElse(null);

        if (user != null) {
            assessment.setUser (user); // Set the user for the assessment
            assessmentRepo.save(assessment); // Save the new assessment to the database
            return "redirect:/addAssessment"; // Redirect to the doctor's home page after successful addition
        } else {
            model.addAttribute("error", "User  not found. Please check the Patient ID.");
            return "addAssessment"; // Return to the add assessment page with an error
        }
    }

    // New method to show the doctor's home page
    @GetMapping("/doctorHome")
    public String showDoctorHome(Model model) {
        // You can add any necessary attributes to the model here
        model.addAttribute("assessments", assessmentRepo.findAll()); // Fetch all assessments to display
        return "doctorHome"; // Return the name of the Thymeleaf template
    }

    @PostMapping("/process_login")
    public String processLogin(@ModelAttribute User user, Model model) {
        // Find the user by username
        User existingUser  = repo.findByUsername(user.getUsername());

        // Check if the user exists and if the password matches
        if (existingUser  != null && passwordEncoder.matches(user.getPassword(), existingUser .getPassword())) {
            // Successful login
            if ("patient".equalsIgnoreCase(existingUser .getRole())) {
                return "patientHome"; // Redirect to patient home page or dashboard
            } else {
                return "doctorHome"; // Redirect to doctor home page
            }
        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password.");
            return "login"; // Return to login form with error
        }
    }
}