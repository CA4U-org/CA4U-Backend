package com.example.ca4u_backend.common.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 요청의 호스트를 기준으로 리다이렉트 URL 결정
        String host = request.getHeader("Host");
        log.debug("Host: {}", host);
        String redirectUri;

        if (host.contains("localhost")) {
            redirectUri = "http://localhost:3000"; // 로컬 리다이렉트 URL
        } else {
            redirectUri = "https://ca4u-75cbe.web.app/"; // 운영 리다이렉트 URL
        }

        // 해당 URL로 리다이렉트
        response.sendRedirect(redirectUri);
    }
}
