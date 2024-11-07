package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.domain.club.dto.ClubReponseDto;
import com.example.ca4u_backend.domain.favorite.Favorite;
import com.example.ca4u_backend.domain.favorite.FavoriteRepository;
import com.example.ca4u_backend.domain.user.User;
import com.example.ca4u_backend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

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

    public String toggleClubFavoriteStatus(long clubId, long userId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        String userName = user.getUsername();
        String clubName = club.getClubNm();

        if (favoriteRepository.existsByUserAndClub(user, club)) {
            favoriteRepository.deleteByUserAndClub(user, club);
            return userName + "님의 " + clubName + " 즐겨찾기를 삭제했습니다.";
        } else {
            favoriteRepository.save(new Favorite(user, club));
            return userName + "님의 " + clubName + " 즐겨찾기를 추가했습니다.";
        }
    }

    public List<ClubReponseDto> getFavoriteClubsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        List<Favorite> favoriteList = favoriteRepository.findByUser(user);
        return favoriteList.stream().map(Favorite::getClub).map(ClubReponseDto::of).toList();
    }
}
