package com.example.ca4u_backend.domain.club.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterListResponseDto {
  // 중앙대 전체 or 교외
  private Map<String, String> campusScopes;

  // 단위 (단과대별, 학과별 단위)
  private List<CollegeDto> colleges; // 단과대 리스트

  // 카테고리
  private Map<String, Long> categories;

  // 형식 (ClubType Enum)
  private Map<String, String> clubTypes;

  // 규모 (ClubSize Enum)
  private Map<String, Map<String, Object>> clubSizes;

  public FilterListResponseDto(
      Map<String, String> campusScopes,
      List<CollegeDto> colleges,
      Map<String, Long> categories,
      Map<String, String> clubTypes,
      Map<String, Map<String, Object>> clubSizes) {
    this.campusScopes = campusScopes;
    this.colleges = colleges;
    this.categories = categories;
    this.clubTypes = clubTypes;
    this.clubSizes = clubSizes;
  }
}
