package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.aiClient.AIClient;
import com.example.ca4u_backend.domain.club.dto.ClubReponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final AIClient aiClient;

    public ClubReponseDto getClubSpec(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
        return ClubReponseDto.of(club);
    }

    public List<ClubReponseDto> getClubsByCategoryId(long categoryId) {
        List<Club> clubList = clubRepository.findByCategoryId(categoryId);
        return clubList.stream().map(ClubReponseDto::of).toList();
    }

    public List<ClubReponseDto> getClubsBySearch(String search) {
        List<Club> clubList = clubRepository.findByClubNmContainingOrBriefDescriptionContaining(search, search);
        return clubList.stream().map(ClubReponseDto::of).toList();
    }

    public List<ClubReponseDto> getRelatedClubs(List<Long> clubId) {
        Set<Long> recommendedClubIds = aiClient.recommendedByContent(clubId);
        List<Club> clubs = clubRepository.findByIdIn(recommendedClubIds);
        return clubs.stream().map(ClubReponseDto::of).toList();
    }

    public List<ClubReponseDto> getRecommendedClubsByUser(Long userId) {
        Set<Long> recommendedClubIds = aiClient.recommendedByUser(userId);
        List<Club> clubs = clubRepository.findByIdIn(recommendedClubIds);
        return clubs.stream().map(ClubReponseDto::of).toList();
    }
}
