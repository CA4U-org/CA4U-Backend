package com.example.ca4u_backend.filter;

import com.example.ca4u_backend.apiResponse.ApiKeyService;
import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiFilter implements Filter {
  private final ApiKeyService apiKeyService;
  private final ObjectMapper objectMapper;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    if (servletRequest instanceof HttpServletRequest
        && servletResponse instanceof HttpServletResponse) {
      HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
      HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

      // 요청에서 API 키 추출
      String apiKey = httpRequest.getHeader("API-KEY");
      if (apiKey == null || !apiKeyService.isValid(apiKey)) {
        // API 키가 없거나 유효하지 않으면 요청 차단
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        httpResponse.setContentType("application/json");
        ApiResponse<Void> apiResponse = ApiResponse.fail("401 Unauthorized");
        // 응답을 JSON으로 변환
        httpResponse.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        return;
      }
    }

    // API 키가 유효하면 요청을 다음 필터로 전달
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
