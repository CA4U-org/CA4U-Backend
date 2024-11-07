package com.example.ca4u_backend.domain.favorite;

import com.example.ca4u_backend.common.entity.BaseEntity;
import com.example.ca4u_backend.domain.club.Club;
import com.example.ca4u_backend.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "FAVORITE")
public class Favorite extends BaseEntity {
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

    public Favorite(User user, Club club) {
        this.user = user;
        this.club = club;
    }
}
