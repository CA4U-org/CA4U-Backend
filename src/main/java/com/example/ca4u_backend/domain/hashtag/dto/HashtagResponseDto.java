package com.example.ca4u_backend.domain.hashtag.dto;

import com.example.ca4u_backend.domain.hashtag.Hashtag;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class HashtagResponseDto {
  private String hashtagNm;
  private String imgUrl;

  public static HashtagResponseDto of(Hashtag hashtag) {
    return HashtagResponseDto.builder()
        .hashtagNm(hashtag.getHashtagNm())
        .imgUrl(hashtag.getImgUrl())
        .build();
  }
}
