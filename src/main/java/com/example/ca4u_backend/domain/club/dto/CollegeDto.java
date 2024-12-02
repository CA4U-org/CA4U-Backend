package com.example.ca4u_backend.domain.club.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CollegeDto {
  private Long collegeId; // 단과대 ID
  private String collegeName; // 단과대 이름
  private List<MajorDto> majors; // 학과 리스트

  public CollegeDto(Long collegeId, String collegeName, List<MajorDto> majors) {
    this.collegeId = collegeId;
    this.collegeName = collegeName;
    this.majors = majors;
  }
}
