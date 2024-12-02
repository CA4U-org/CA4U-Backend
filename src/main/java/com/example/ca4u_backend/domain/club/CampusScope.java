package com.example.ca4u_backend.domain.club;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CampusScope {
  INTERNAL("중앙대 전체"),
  EXTERNAL("교외");

  private final String description;

  CampusScope(String description) {
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
    return Arrays.stream(CampusScope.values())
        .collect(Collectors.toMap(Enum::name, CampusScope::getDescription));
  }
}
