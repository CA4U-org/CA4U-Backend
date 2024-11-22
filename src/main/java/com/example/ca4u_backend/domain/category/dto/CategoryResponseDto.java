package com.example.ca4u_backend.domain.category.dto;

import com.example.ca4u_backend.domain.category.Category;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
  private Long id;
  private String categoryNm;
  private String imgUrl;

  public static CategoryResponseDto of(Category category) {
    return CategoryResponseDto.builder()
        .id(category.getId())
        .categoryNm(category.getCategoryNm())
        .imgUrl(category.getImgUrl())
        .build();
  }
}
