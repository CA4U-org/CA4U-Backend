package com.example.ca4u_backend.domain.club.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MajorDto {
  private Long majorId; // 학과 ID
  private String majorName; // 학과 이름

  public MajorDto(Long majorId, String majorName) {
    this.majorId = majorId;
    this.majorName = majorName;
  }
}
