package com.CareNet.CN.controller;

import com.CareNet.CN.model.Patient;
import com.CareNet.CN.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class Doctor {
    private final PatientService patientService;

    public Doctor(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("/patients")
    public String doctorDashboard(Model model) {
        List<Patient> patients = patientService.getPendingPatients();
        model.addAttribute("patients", patients);
        return "doctor_dashboard";
    }

    @PostMapping("/patients/{id}/accept")
    public String acceptPatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patientService.acceptPatient(id);
        redirectAttributes.addFlashAttribute("message", "Your request is accepted and the details will be sent through your email.");
        return "redirect:/doctor/patients";
    }


    @PostMapping("/patients/{id}/decline")
    public String declinePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patientService.declinePatient(id);
        redirectAttributes.addFlashAttribute("message", "The patient has been declined.");
        return "redirect:/doctor/patients";
    }
}
