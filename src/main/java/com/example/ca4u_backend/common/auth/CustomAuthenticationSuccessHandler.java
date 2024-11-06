package com.example.ca4u_backend.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // state 파라미터를 가져와 로컬 또는 운영 환경에 따라 리디렉트
        String state = request.getParameter("state");
        String redirectUrl;

        if ("local".equals(state)) {
            redirectUrl = "http://localhost:3000"; // 로컬 리디렉트 URL
        } else {
            redirectUrl = "https://ca4u-75cbe.web.app/"; // 운영 리디렉트 URL
        }

        response.sendRedirect(redirectUrl);
    }
}
