package com.CareNet.CN.controller;

import com.CareNet.CN.model.PatientAssessment;
import com.CareNet.CN.service.PatientAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AssessmentController {

    @Autowired
    private PatientAssessmentService patientAssessmentService;

    @GetMapping("/assessment")
    public String showAssessmentPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<PatientAssessment> assessments = patientAssessmentService.getAssessmentsByUsername(username);
        model.addAttribute("assessments", assessments);

        return "assessment";
    }
    @GetMapping("/search-assessments")
    public String searchAssessments(@RequestParam String username, Model model) {
        // Fetch assessments for the given username
        List<PatientAssessment> assessments = patientAssessmentService.getAssessmentsByUsername(username);

        // Add data to the model for rendering in Thymeleaf
        model.addAttribute("assessments", assessments);
        model.addAttribute("searchedUsername", username);

        // Render the assessment view
        return "assessment";
    }

}
