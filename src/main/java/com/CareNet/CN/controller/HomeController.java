package com.CareNet.CN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToRegister() {
        return "registerpage"; // Return the name of the template without the .html extension
    }

    @GetMapping("/registerpage")
    public String showRegisterPage() {
        return "registerpage"; // Serve the registration page
    }
}