package com.example.ca4u_backend.common.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    String state = request.getParameter("state");
    String redirectUrl;

    if ("local".equals(state)) {
      redirectUrl = "http://localhost:3000";
    } else {
      redirectUrl = "https://ca4u.store/";
    }

    response.sendRedirect(redirectUrl);
  }
}
