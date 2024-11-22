package com.example.ca4u_backend.domain.club.dto;

import com.example.ca4u_backend.domain.club.Club;
import java.time.format.DateTimeFormatter;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponseDto {
  private Long id;
  // 클럽명
  private String clubNm;
  // 클럽간략설명
  private String briefDescription;
  // 모집공고글
  private String recruitDescription;
  // 선발대상설명
  private String targetPeopleDescription;
  // 선발주기설명
  private String targetCycleDescription;
  // 지원방법설명
  private String applyDescription;
  // 활동요일설명
  private String actDayDescription;
  // 활동장소_설명 (동아리방 위치 등)
  private String locationDescription;
  // 회비 설명
  private String costDescription;
  // 클럽 상세 설명 (About 클럽)
  private String specDescription;
  // 클럽 로고 이미지 주소
  private String logoImgUrl;
  // 업데이트 날짜
  private String updatedAt;

  /*    // 클럽 썸네일 이미지 주소
  private List<String> thumbnailImgUrlList;*/

  public static ClubResponseDto of(Club club) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd"); // 원하는 포맷

    return ClubResponseDto.builder()
        .id(club.getId())
        .clubNm(club.getClubNm())
        .briefDescription(club.getBriefDescription())
        .recruitDescription(club.getRecruitDescription())
        .targetPeopleDescription(club.getTargetPeopleDescription())
        .targetCycleDescription(club.getTargetCycleDescription())
        .applyDescription(club.getApplyDescription())
        .actDayDescription(club.getActDayDescription())
        .locationDescription(club.getLocationDescription())
        .costDescription(club.getCostDescription())
        .specDescription(club.getSpecDescription())
        .logoImgUrl(club.getLogoImgUrl())
        .updatedAt(club.getUpdatedAt().format(formatter))
        /*      .thumbnailImgUrlList(club.getThumbnailImgUrlList())*/
        .build();
  }
}
