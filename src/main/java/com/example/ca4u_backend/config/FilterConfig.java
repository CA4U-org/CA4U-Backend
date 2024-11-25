package com.example.ca4u_backend.config;

import com.example.ca4u_backend.apiResponse.ApiKeyService;
import com.example.ca4u_backend.filter.ApiFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {
  private final ApiKeyService apiKeyService;
  private final ObjectMapper objectMapper;

  @Bean
  public FilterRegistrationBean<ApiFilter> apiFilter() {
    FilterRegistrationBean<ApiFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ApiFilter(apiKeyService, objectMapper));
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1); // 필터의 순서를 지정
    registrationBean.setName("ApiSecretFilter");
    return registrationBean;
  }
}
