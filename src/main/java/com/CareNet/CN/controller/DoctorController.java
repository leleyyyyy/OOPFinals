package com.CareNet.CN.controller;

import com.CareNet.CN.model.Doctor;
import com.CareNet.CN.model.User;
import com.CareNet.CN.service.DoctorService;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;


    @GetMapping("/doctor/profile")
    public String viewDoctorProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        Optional<Doctor> doctorOptional = doctorService.getDoctorByUser(user);
        if (doctorOptional.isPresent()) {
            model.addAttribute("doctor", doctorOptional.get());
        } else {
            model.addAttribute("error", "Doctor profile not found.");
        }

        return "doctor_profile";
    }

}