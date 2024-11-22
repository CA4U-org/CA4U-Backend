package com.example.ca4u_backend.domain.user.dto;

public interface OAuth2Response {
  String getProvider();

  String getProviderId();

  String getEmail();

  String getName();
}
