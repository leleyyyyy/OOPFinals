package com.CareNet.CN.service;

import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser (User user, BindingResult result) {
        // Check if the username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email already exists");
        }

        // If there are errors, return an error message
        if (result.hasErrors()) {
            return "register";
        }

        // Save the user to the database
        userRepository.save(user);
        return "success";
    }
}