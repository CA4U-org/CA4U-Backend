package com.example.ca4u_backend.domain.user.dto;

import com.example.ca4u_backend.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {
  private Long id;
  private String email;
  private String name;
  private String department;
  private String major;
  private boolean isRegistered;

  public static UserResponseDto from(User user) {
    return new UserResponseDto(
        user.getId(),
        user.getEmail(),
        user.getName(),
        user.getDepartment(),
        user.getMajor(),
        user.isRegistered());
  }
}
