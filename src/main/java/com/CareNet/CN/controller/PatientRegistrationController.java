package com.CareNet.CN.controller;

import com.CareNet.CN.model.Patient;
import com.CareNet.CN.model.PatientRegistrationDTO;
import com.CareNet.CN.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class PatientRegistrationController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/register/patient")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patientDTO", new PatientRegistrationDTO());
        return "patientRegistration";
    }

    @PostMapping("/register/patient")
    public String registerPatient(@ModelAttribute("patientDTO") PatientRegistrationDTO dto, Model model) {
        Patient patient = new Patient(dto.getName(), dto.getAppointmentDate().toString());
        patientService.savePatient(patient);
        model.addAttribute("success", "Patient registered successfully, We will reach out for further update");
        return "patientRegistration";
    }
}

