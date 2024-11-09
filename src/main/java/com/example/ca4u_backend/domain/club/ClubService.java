package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.domain.club.dto.ClubReponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClubService {
    private final ClubRepository clubRepository;

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

    public List<ClubReponseDto> getClubsInIds(Collection<Long> clubIds) {
        List<Club> clubList = clubRepository.findByIdIn(clubIds);
        return clubList.stream().map(ClubReponseDto::of).toList();
    }
}
