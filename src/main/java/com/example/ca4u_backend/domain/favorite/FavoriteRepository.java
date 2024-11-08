package com.example.ca4u_backend.domain.favorite;

import com.example.ca4u_backend.domain.club.Club;
import com.example.ca4u_backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("SELECT f FROM FAVORITE f JOIN FETCH f.club WHERE f.user = :user")
    List<Favorite> findByUser(User user);

    boolean existsByUserAndClub(User user, Club club);
    void deleteByUserAndClub(User user, Club club);
}
