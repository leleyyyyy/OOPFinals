package com.CareNet.CN.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            if ("ROLE_PATIENT".equals(role)) {
                response.sendRedirect("/patientHome");
                return;
            } else if ("ROLE_DOCTOR".equals(role)) {
                response.sendRedirect("/doctorHome");
                return;
            }
        }

        response.sendRedirect("/"); // fallback
    }
}
