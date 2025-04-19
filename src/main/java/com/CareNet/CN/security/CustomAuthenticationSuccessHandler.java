package com.CareNet.CN.security;

import com.CareNet.CN.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String redirectURL = request.getContextPath();

        switch (user.getRole()) {
            case DOCTOR:
                redirectURL += "/doctor/doctorHome";
                break;
            case PATIENT:
                redirectURL += "/patient/patientHome";
                break;
            default:
                redirectURL += "/login"; // fallback
        }

        response.sendRedirect(redirectURL);
    }
}
