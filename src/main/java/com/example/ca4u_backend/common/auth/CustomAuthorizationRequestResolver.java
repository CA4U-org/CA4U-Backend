package com.example.ca4u_backend.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

  private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

  public CustomAuthorizationRequestResolver(
      ClientRegistrationRepository clientRegistrationRepository) {
    this.defaultResolver =
        new DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository, "/oauth2/authorization");
  }

  @Override
  public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
    return addCustomState(defaultResolver.resolve(request), request);
  }

  @Override
  public OAuth2AuthorizationRequest resolve(
      HttpServletRequest request, String clientRegistrationId) {
    return addCustomState(defaultResolver.resolve(request, clientRegistrationId), request);
  }

  private OAuth2AuthorizationRequest addCustomState(
      OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request) {
    if (authorizationRequest != null) {
      // Referer 헤더를 확인하여 로컬 환경이면 state에 "local"을 추가
      String referer = request.getHeader("Referer");
      String customState = (referer != null && referer.contains("localhost")) ? "local" : "prod";

      return OAuth2AuthorizationRequest.from(authorizationRequest)
          .state(customState) // 환경 정보 추가
          .build();
    }
    return authorizationRequest;
  }
}
