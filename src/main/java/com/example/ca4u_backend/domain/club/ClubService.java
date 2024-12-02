package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.aiClient.AIClient;
import com.example.ca4u_backend.common.utils.CollectionsUtil;
import com.example.ca4u_backend.domain.category.Category;
import com.example.ca4u_backend.domain.category.CategoryRepository;
import com.example.ca4u_backend.domain.club.dto.ClubResponseDto;
import com.example.ca4u_backend.domain.club.dto.CollegeDto;
import com.example.ca4u_backend.domain.club.dto.FilterListResponseDto;
import com.example.ca4u_backend.domain.club.dto.MajorDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClubService {
  private final ClubRepository clubRepository;
  private final AIClient aiClient;
  private final CategoryRepository categoryRepository;

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
      List<String> sizes) {
    List<Club> allClubs = clubRepository.findAll();

    return allClubs.stream()
        // 모집 여부 필터링
        .filter(club -> isRecruit == null || club.getIsRecruit().equals(isRecruit))

        // 캠퍼스 범위 필터링 (교내/교외)
        .filter(club -> campusScope == null || club.getCampusScope().equals(campusScope))

        // 단과대학 필터링
        .filter(
            club ->
                collegeId == null
                    || club.getCollege() != null && club.getCollege().getId().equals(collegeId))

        // 학과 필터링
        .filter(
            club ->
                majorId == null
                    || club.getMajor() != null && club.getMajor().getId().equals(majorId))

        // 카테고리 필터링
        .filter(
            club ->
                CollectionsUtil.isEmpty(categoryIds)
                    || categoryIds.contains(club.getCategory().getId()))

        // 형식 필터링 (동아리, 학회, 소모임, 스터디 등)
        .filter(
            club -> CollectionsUtil.isEmpty(categoryIds) || clubTypes.contains(club.getClubType()))

        // 규모 필터링 (대규모, 중규모, 소규모)
        .filter(
            club -> {
              if (CollectionsUtil.isEmpty(sizes)) return true; // 필터링 없을 경우 모든 클럽 포함
              Integer membership = club.getMembership(); // 회원수
              if (membership == null) return false; // 회원수 정보가 없는 클럽 제외
              return sizes.stream().anyMatch(size -> matchSize(size, membership));
            })

        // DTO로 변환
        .map(ClubResponseDto::of)
        .toList();
  }

  public FilterListResponseDto getFilterList() {
    // 중앙대 캠퍼스 스코프
    Map<String, String> campusScopes = CampusScope.toMap();

    // 단과대와 학과 데이터
    List<CollegeDto> colleges = getCollegesWithMajors();

    // 카테고리 데이터
    Map<String, Long> categories = getCategoriesAsMap();

    // 클럽 형식 (ClubType Enum을 Map으로 변환)
    Map<String, String> clubTypes = ClubType.toMap();

    // 클럽 규모 (ClubSize Enum을 Map으로 변환)
    Map<String, Map<String, Object>> clubSizes = ClubSize.toMap();

    // DTO 반환
    return new FilterListResponseDto(campusScopes, colleges, categories, clubTypes, clubSizes);
  }



  private boolean matchSize(String size, int membership) {
    return switch (size) {
      case "LARGE" -> membership >= 101; // 대규모
      case "MEDIUM" -> membership >= 50 && membership <= 100; // 중규모
      case "SMALL" -> membership < 50; // 소규모
      default -> false; // 정의되지 않은 크기
    };
  }

  private Map<String, Long> getCategoriesAsMap() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
        .collect(
            Collectors.toMap(
                Category::getCategoryNm, // Key: 카테고리 이름
                Category::getId // Value: 카테고리 ID
                ));
  }

  private List<CollegeDto> getCollegesWithMajors() {
    // 네이티브 쿼리 결과 가져오기
    List<Object[]> results = clubRepository.findCollegesWithMajorsNative();

    // 결과 매핑
    Map<Long, CollegeDto> collegeMap = new LinkedHashMap<>();

    for (Object[] row : results) {
      Long collegeId = ((Number) row[0]).longValue();
      String collegeName = (String) row[1];
      Long majorId = ((Number) row[2]).longValue();
      String majorName = (String) row[3];

      // 단과대 생성 또는 가져오기
      CollegeDto college =
          collegeMap.computeIfAbsent(
              collegeId, id -> new CollegeDto(id, collegeName, new ArrayList<>()));

      // 학과 추가
      college.getMajors().add(new MajorDto(majorId, majorName));
    }

    return new ArrayList<>(collegeMap.values());
  }
}
