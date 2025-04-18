package com.CareNet.CN.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean // Hashes the password for security
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/registerpage", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/success", true) // Redirect to success page after login
                        .failureUrl("/login?error=true") // Redirect to login page on failure
                        .permitAll() // Allow everyone to see the login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/registerpage") // Redirect to register page after logout
                        .permitAll() // Allow everyone to log out
                );

        return http.build();
    }
}