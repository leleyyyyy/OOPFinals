package com.CareNet.CN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home") // Change the mapping to /home
    public String redirectToRegistration() {
        return "redirect:/register"; // Redirect to the registration page
    }
}