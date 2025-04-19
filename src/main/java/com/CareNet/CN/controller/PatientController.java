package com.CareNet.CN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @GetMapping("/patientHome")
    public String patientHome(Model model) {
        // Logic to send patient data to the view
        return "patientHome"; // Returns patient home template
    }
}
