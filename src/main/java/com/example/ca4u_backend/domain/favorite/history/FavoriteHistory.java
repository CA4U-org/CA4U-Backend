package com.example.ca4u_backend.domain.favorite.history;

import com.example.ca4u_backend.common.entity.BaseEntity;
import com.example.ca4u_backend.domain.club.Club;
import com.example.ca4u_backend.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "FAVORITE_HISTORY")
public class FavoriteHistory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "club_id", nullable = false)
  private Club club;

  // 즐겨찾기를 추가한건지 삭제한건지 이력을 구분할 수 있는 필드추가
  @Column(name = "action_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private ActionType actionType;

  public FavoriteHistory(User user, Club club, ActionType actionType) {
    this.user = user;
    this.club = club;
    this.actionType = actionType;
  }
}
