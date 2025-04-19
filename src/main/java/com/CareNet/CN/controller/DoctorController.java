package com.CareNet.CN.controller;

//import ch.qos.logback.core.model.Model;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/doctorHome")
    public String doctorHome(Model model) {
        // Logic to send doctor data to the view
        return "doctorHome"; // Returns doctor home template
    }
}