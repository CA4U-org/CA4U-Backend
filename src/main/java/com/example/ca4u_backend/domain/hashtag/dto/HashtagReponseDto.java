package com.example.ca4u_backend.domain.hashtag.dto;

import com.example.ca4u_backend.domain.hashtag.Hashtag;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class HashtagReponseDto {
    private String hashtagNm;
    private String imgUrl;

    public static HashtagReponseDto of(Hashtag hashtag){
        return HashtagReponseDto.builder()
                .hashtagNm(hashtag.getHashtagNm())
                .imgUrl(hashtag.getImgUrl())
                .build();
    }
}
