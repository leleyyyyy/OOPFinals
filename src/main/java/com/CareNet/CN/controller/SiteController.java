package com.CareNet.CN.controller;

import com.CareNet.CN.model.DoctorAppointment;
import com.CareNet.CN.model.PatientAssessment;
import com.CareNet.CN.model.AppointmentSetter;
import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.PatientAssessmentRepository;
import com.CareNet.CN.repository.UserRepository;
import com.CareNet.CN.service.PatientAssessmentService;
import com.CareNet.CN.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @Autowired
    private PatientAssessmentService patientAssessmentService;


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
        model.addAttribute("patientDTO", new AppointmentSetter());
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
    public String showDoctorHome(@RequestParam(value = "username", required = false) String username, Model model) {
        // Fetch patients with the role "ROLE_PATIENT" directly from the database
        List<User> patients = userRepository.findByRole("ROLE_PATIENT");

        // Debugging log (optional, can be removed)
        System.out.println("Patients fetched: " + patients);

        // Add the patients list to the model
        model.addAttribute("patients", patients);

        // If a username is provided in the search bar, fetch and add the assessments
        if (username != null && !username.isEmpty()) {
            List<PatientAssessment> assessments = patientAssessmentService.getAssessmentsByUsername(username);
            model.addAttribute("assessments", assessments);
            model.addAttribute("searchedUsername", username);
        }

        // Return the doctorHome view
        return "doctorHome";
    }


    // Redirect to doctor registration page

    @GetMapping("register/doctor")
    public String doctorDashboard(Model model) {
        List<DoctorAppointment> doctorAppointments = patientService.getPendingPatients();
        model.addAttribute("appointments", doctorAppointments);
        return "doctor_dashboard";
    }

    @PostMapping("register/doctor/{id}/accept")
    public String acceptPatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patientService.acceptPatient(id);
        redirectAttributes.addFlashAttribute("message", "The patient has been accepted.");
        return "redirect:/register/doctor";
    }


    @PostMapping("register/doctor/{id}/decline")
    public String declinePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patientService.declinePatient(id);
        redirectAttributes.addFlashAttribute("message", "The patient has been declined.");
        return "redirect:/register/doctor";
    }

    // Redirect to patient registration page
    @GetMapping("/appointment/doctorAppointment")
    public String showPatientRegistrationForm(Model model) {
        model.addAttribute("patientDTO", new AppointmentSetter()); // Make sure this class exists
        return "appointment";
    }

    @PostMapping("/doctorAppointment/appointment")
    public String registerPatient(@ModelAttribute("patientDTO") AppointmentSetter dto, RedirectAttributes redirectAttributes) {
        DoctorAppointment doctorAppointment = new DoctorAppointment(dto.getName(), dto.getAppointmentDate().toString());
        patientService.savePatient(doctorAppointment);
        redirectAttributes.addFlashAttribute("success", "Appointment booked successfully.");
        return "redirect:/patientHome";
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
