package com.CareNet.CN.controller;

import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.UserRepository;
import com.CareNet.CN.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userRepository.findByUsername(username); // fetch actual User entity
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        User user = getLoggedInUser(authentication);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfileForm(Model model, Authentication authentication) {
        User user = getLoggedInUser(authentication);
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Authentication authentication) {
        User existingUser = getLoggedInUser(authentication);

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setMiddleName(updatedUser.getMiddleName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setBirthday(updatedUser.getBirthday());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setBloodType(updatedUser.getBloodType());
        existingUser.setEmergencyContact(updatedUser.getEmergencyContact());
        existingUser.setImageUrl(updatedUser.getImageUrl());

        userRepository.save(existingUser);

        return "redirect:/profile";
    }
}
