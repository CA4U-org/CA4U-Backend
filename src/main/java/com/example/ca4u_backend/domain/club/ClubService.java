package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.aiClient.AIClient;
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
}
