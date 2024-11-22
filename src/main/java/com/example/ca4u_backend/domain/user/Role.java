package com.example.ca4u_backend.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
  USER("ROLE_USER", "일반 사용자");

  private final String key;
  private final String title;

  @Override
  public String getAuthority() {
    return key;
  }
}
