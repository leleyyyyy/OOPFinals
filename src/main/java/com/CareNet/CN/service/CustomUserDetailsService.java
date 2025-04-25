//package com.CareNet.CN.service;
//
//import com.CareNet.CN.model.CustomUserDetails;
//import com.CareNet.CN.model.User;
//import com.CareNet.CN.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username); // or findByEmail if using email
//        if (user == null) {
//            logger.error("User not found with username: {}", username);
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new CustomUserDetails(user);
//    }
//}
