package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.aiClient.AIClient;
import com.example.ca4u_backend.common.utils.CollectionsUtil;
import com.example.ca4u_backend.domain.club.dto.ClubResponseDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClubService {
  private final ClubRepository clubRepository;
  private final AIClient aiClient;

  public ClubResponseDto getClubSpec(long clubId) {
    Club club =
        clubRepository
            .findById(clubId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    return ClubResponseDto.of(club);
  }

  public List<ClubResponseDto> getClubsByCategoryId(long categoryId) {
    List<Club> clubList = clubRepository.findByCategoryId(categoryId);
    return clubList.stream().map(ClubResponseDto::of).toList();
  }

  public List<ClubResponseDto> getClubsBySearch(String search) {
    List<Club> clubList =
        clubRepository.findByClubNmContainingOrBriefDescriptionContaining(search, search);
    return clubList.stream().map(ClubResponseDto::of).toList();
  }

  public List<ClubResponseDto> getRelatedClubs(List<Long> clubId) {
    Set<Long> recommendedClubIds = aiClient.recommendedByContent(clubId);
    List<Club> clubs = clubRepository.findByIdIn(recommendedClubIds);
    return clubs.stream().map(ClubResponseDto::of).toList();
  }

  public List<ClubResponseDto> getRecommendedClubsByUser(Long userId) {
    Set<Long> recommendedClubIds = aiClient.recommendedByUser(userId);
    List<Club> clubs = clubRepository.findByIdIn(recommendedClubIds);
    return clubs.stream().map(ClubResponseDto::of).toList();
  }

  public List<ClubResponseDto> getFilteredClubs(
          Boolean isRecruit,
          CampusScope campusScope,
          Long collegeId,
          Long majorId,
          List<Long> categoryIds,
          List<ClubType> clubTypes,
          List<String> sizes
  ) {
    List<Club> allClubs = clubRepository.findAll();

    return allClubs.stream()
            // 모집 여부 필터링
            .filter(club -> isRecruit == null || club.getIsRecruit().equals(isRecruit))

            // 캠퍼스 범위 필터링 (교내/교외)
            .filter(club -> campusScope == null || club.getCampusScope().equals(campusScope))

            // 단과대학 필터링
            .filter(club -> collegeId == null || club.getCollege() != null && club.getCollege().getId().equals(collegeId))

            // 학과 필터링
            .filter(club -> majorId == null || club.getMajor() != null && club.getMajor().getId().equals(majorId))

            // 카테고리 필터링
            .filter(club -> CollectionsUtil.isEmpty(categoryIds) || categoryIds.contains(club.getCategory().getId()))

            // 형식 필터링 (동아리, 학회, 소모임, 스터디 등)
            .filter(club -> CollectionsUtil.isEmpty(categoryIds) || clubTypes.contains(club.getClubType()))

            // 규모 필터링 (대규모, 중규모, 소규모)
            .filter(club -> {
              if (CollectionsUtil.isEmpty(sizes)) return true;
              String size = club.getSize();
              return size != null && sizes.contains(size);
            })

            // DTO로 변환
            .map(ClubResponseDto::of)
            .toList();
  }

}
