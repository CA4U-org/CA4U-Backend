package com.example.ca4u_backend.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
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

    if (authorizationRequest == null) {
      return null;
    }

    String referer = request.getHeader(HttpHeaders.REFERER);
    AuthorizationRequestState state =
        (referer != null && referer.contains("localhost"))
            ? AuthorizationRequestState.LOCAL
            : AuthorizationRequestState.PRODUCTION;

    return OAuth2AuthorizationRequest.from(authorizationRequest).state(state.name()).build();
  }
}
