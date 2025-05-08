package com.CareNet.CN.controller;

import com.CareNet.CN.model.DoctorRegistrationDTO;
import com.CareNet.CN.model.Doctor;
import com.CareNet.CN.model.Role;
import com.CareNet.CN.model.User;
import com.CareNet.CN.service.DocService;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DocController {

    @Autowired
    private DocService docService;

    @Autowired
    private UserService userService;

    @GetMapping("/doctor/register")
    public String showRegistrationForm() {
        return "doctorRegistration"; // Return the registration form template
    }

    @PostMapping("/doctor/register")
    public String registerDoctor(DoctorRegistrationDTO registrationDTO, Authentication authentication, Model model) {
        // Convert DTO to Doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(registrationDTO.getName());
        doctor.setSpecialty(registrationDTO.getSpecialty());
        doctor.setEmail(registrationDTO.getEmail());
        doctor.setPhone(registrationDTO.getPhone());
        doctor.setBio(registrationDTO.getBio());
        doctor.setRole(Role.DOCTOR); // Set the role for the doctor

        // Associate doctor with the logged-in user
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        doctor.setUser(user); // Link doctor to user

        // Save the doctor in the database
        docService.saveDoctor(doctor);

        model.addAttribute("success", "Doctor registered successfully!");
        return "redirect:/doctor/profile"; // This will redirect to the new doctor profile page
    }
}

