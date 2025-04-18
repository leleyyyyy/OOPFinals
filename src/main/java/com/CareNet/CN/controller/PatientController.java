package com.CareNet.CN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @GetMapping("/home")
    public String patientHome() {
        return "patientHome"; // Return the patient's homepage view
    }
}
