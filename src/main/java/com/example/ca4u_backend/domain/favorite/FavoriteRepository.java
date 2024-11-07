package com.example.ca4u_backend.domain.favorite;

import com.example.ca4u_backend.domain.club.Club;
import com.example.ca4u_backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    boolean existsByUserAndClub(User user, Club club);
    void deleteByUserAndClub(User user, Club club);
}
