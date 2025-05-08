package com.CareNet.CN.controller;

import com.CareNet.CN.model.Patient;
import com.CareNet.CN.model.PatientAssessment;
import com.CareNet.CN.model.PatientRegistrationDTO;
import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.PatientAssessmentRepository;
import com.CareNet.CN.repository.UserRepository;
import com.CareNet.CN.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SiteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientAssessmentRepository patientAssessmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientService patientService;

    @GetMapping("/lobby")
    public String showLobbyPage() {
        return "lobby";  // This corresponds to your lobby.html page
    }

    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/lobby";
    }

    @GetMapping("/logout")
    public String logout() {
        // You can add logic for handling session logout if needed
        return "redirect:/lobby";  // Redirect to the lobby after logout
    }

    @GetMapping("/register")
    public String showSignUp(Model model) {
        model.addAttribute("user", new User());
        return "registerpage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/patientHome")
    public String showPatientHome(Model model) {
        User user = getCurrentUser(); // Get the current authenticated user
        if (user == null) {
            return "redirect:/login"; // Redirect if no user is authenticated
        }
        model.addAttribute("user", user); // Add the user object to the model
        return "patientHome"; // Render the patientHome Thymeleaf template
    }
    private User getCurrentUser() {
        // Get the current authenticated user from the SecurityContextHolder
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails != null) {
            // Fetch the user entity from the database using the username
            return userRepository.findByUsername(userDetails.getUsername());
        }
        return null; // Return null if the user is not authenticated
    }


    @GetMapping("/doctorHome")
    public String showLobby() {
        return "doctorHome";  // Redirects to lobby.html
    }

    // Redirect to doctor registration page
    @GetMapping("/register/doctor")
    public String showDoctorRegistration() {
        return "doctor_dashboard";  // Your doctor registration form page
    }

    // Redirect to patient registration page
    @GetMapping("/appointment/patient")
    public String showPatientRegistrationForm(Model model) {
        model.addAttribute("patientDTO", new PatientRegistrationDTO()); // Make sure this class exists
        return "patientRegistration";
    }

    @PostMapping("/patient/appointment")
    public String registerPatient(@ModelAttribute("patientDTO") PatientRegistrationDTO dto, Model model) {
        Patient patient = new Patient(dto.getName(), dto.getAppointmentDate().toString());
        patientService.savePatient(patient);
        model.addAttribute("success", "Patient registered successfully!,We will reach out for further update");
        return "patientRegistration";
    }
    // Mapping for the "addAssessment" page
    @GetMapping("/addAssessment")
    public String showAddAssessmentForm(Model model) {
        // Add an empty PatientAssessment object to the model to bind the form inputs
        model.addAttribute("patientAssessment", new PatientAssessment());
        return "addAssessment"; // This should correspond to the name of the HTML page (e.g., addAssessment.html)
    }

    // Mapping to handle the form submission (POST request)
    @PostMapping("/addAssessment")
    public String addAssessment(@ModelAttribute PatientAssessment patientAssessment) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming username is unique
        User user = findUserByUsername(username); // You need to implement this method or use a service to fetch the user

        // Set the user on the patient assessment
        patientAssessment.setUser(user);

        // Save the patient assessment
        patientAssessmentRepository.save(patientAssessment);

        return "redirect:/doctorHome"; // Redirect to a list of assessments or another page
    }

    // You need to implement this method to find the User by username (or use a service to fetch the User)
    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username); // You need to inject userRepository if you don't already
    }

        @PostMapping("/process_register")
    public String processRegistration(@ModelAttribute User user, Model model) {
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Username is already taken.");
            return "registerpage";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email is already registered.");
            return "registerpage";
        }

        user.setRole("ROLE_PATIENT");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/success")
    public String redirectBasedOnRole() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCTOR"))) {
            return "redirect:/doctorHome";
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            return "redirect:/patientHome";
        }
        return "redirect:/login";
    }
}
