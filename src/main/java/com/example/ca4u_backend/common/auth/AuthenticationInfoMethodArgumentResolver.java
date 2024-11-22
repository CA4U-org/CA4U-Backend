package com.example.ca4u_backend.common.auth;

import com.example.ca4u_backend.domain.user.dto.CustomOAuth2User;
import com.example.ca4u_backend.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/** Handler Parameter에 로그인한 사용자의 ID를 주입합니다. */
@Component
@Slf4j
public class AuthenticationInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(Long.class)
        && parameter.hasParameterAnnotation(Auth.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws Exception {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.isAuthenticated() && !isAnonymous(authentication)) {
      CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
      return principal.getId();
    } else {
      Auth authAnnotation = parameter.getParameterAnnotation(Auth.class);
      if (authAnnotation.required()) {
        throw new BaseException("로그인이 필요합니다.");
      }
      return null;
    }
  }

  private boolean isAnonymous(Authentication authentication) {
    return authentication == null || "anonymousUser".equals(authentication.getPrincipal());
  }
}
