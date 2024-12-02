package com.example.ca4u_backend.domain.club;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/*
CLUB - 동아리
ACADEMIC - 학회
community - 소모임
STUDY - 스터디
OTHER - 기타
*/

public enum ClubType {
  CLUB("동아리"),
  ACADEMIC("학회"),
  COMMUNITY("소모임"),
  STUDY("스터디"),
  OTHER("기타");

  private final String description;

  ClubType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Key-Value 형태로 Enum 값 반환
   *
   * @return Map<String, String> 형태로 반환 (Key: Enum 이름, Value: 설명)
   */
  public static Map<String, String> toMap() {
    return Arrays.stream(ClubType.values())
        .collect(Collectors.toMap(Enum::name, ClubType::getDescription));
  }
}
