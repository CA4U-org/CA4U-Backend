package com.example.ca4u_backend.domain.club;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ClubSize {
  LARGE("대규모"),
  MEDIUM("중규모"),
  SMALL("소규모");

  private final String description;

  ClubSize(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Key-Value 형태로 Enum 값 반환
   *
   * @return Map<String, Map<String, Object>> 형태로 반환 (Key: Enum 이름, Value: 설명과 숫자 값)
   */
  public static Map<String, Map<String, Object>> toMap() {
    return Arrays.stream(ClubSize.values())
        .collect(
            Collectors.toMap(
                Enum::name,
                size ->
                    Map.of(
                        "description", size.getDescription())));
  }
}
