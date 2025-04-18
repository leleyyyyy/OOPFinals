package com.CareNet.CN.service;

import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import the Transactional annotation
import org.springframework.validation.BindingResult;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

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
            return "register"; // Return to registration page if there are errors
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        saveUser (user); // Call the saveUser  method
        return "success"; // Return success message or view
    }

    @Transactional // Ensure this method runs in a transaction
    public void saveUser (User user) {
        userRepository.save(user); // Save the user to the database
    }

    public User authenticateUser (String username, String password) {
        User user = userRepository.findByUsername(username); // Fetch user by username
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Return user if credentials match
        }
        return null; // Return null if authentication fails
    }
}