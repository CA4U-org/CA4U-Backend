package com.example.ca4u_backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateStudentInfoRequest {
  private String name;
  private String department;
  private String major;
}
