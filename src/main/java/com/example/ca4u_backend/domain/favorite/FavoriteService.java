package com.example.ca4u_backend.domain.favorite;

import com.example.ca4u_backend.domain.club.Club;
import com.example.ca4u_backend.domain.club.ClubRepository;
import com.example.ca4u_backend.domain.club.dto.ClubResponseDto;
import com.example.ca4u_backend.domain.favorite.history.ActionType;
import com.example.ca4u_backend.domain.favorite.history.FavoriteHistory;
import com.example.ca4u_backend.domain.favorite.history.FavoriteHistoryRepository;
import com.example.ca4u_backend.domain.user.User;
import com.example.ca4u_backend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FavoriteService {
  private final ClubRepository clubRepository;
  private final FavoriteRepository favoriteRepository;
  private final FavoriteHistoryRepository favoriteHistoryRepository;
  private final UserRepository userRepository;

  @Transactional
  public boolean toggleClubFavoriteStatus(long clubId, long userId) {
    Club club =
        clubRepository
            .findById(clubId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 클럽입니다."));
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

    if (favoriteRepository.existsByUserAndClub(user, club)) {
      favoriteRepository.deleteByUserAndClub(user, club);
      // 이력 추가
      favoriteHistoryRepository.save(new FavoriteHistory(user, club, ActionType.REMOVE));
      return true;

    } else {
      favoriteRepository.save(new Favorite(user, club));
      // 이력 추가
      favoriteHistoryRepository.save(new FavoriteHistory(user, club, ActionType.ADD));
      return false;
    }
  }

  public List<ClubResponseDto> getFavoriteClubsByUserId(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    List<Favorite> favoriteList = favoriteRepository.findByUser(user);
    return favoriteList.stream().map(Favorite::getClub).map(ClubResponseDto::of).toList();
  }
}
