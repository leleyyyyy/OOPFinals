package com.CareNet.CN.controller;

import com.CareNet.CN.model.User;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/someOtherPath")
    public String someMethod() {
        return "redirect:/registerpage"; // Ensure this points to the new template name
    }

    @PostMapping("/register")
    public String registerUser (@Valid User user, BindingResult result, Model model) {
        String viewName = userService.registerUser (user, result);
        if (viewName.equals("register")) {
            return "register"; // Return to the registration form if there are errors
        }
        model.addAttribute("message", "User  registered successfully!");
        return "success"; // Redirect to success page
    }
}