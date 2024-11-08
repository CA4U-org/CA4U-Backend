package com.example.ca4u_backend.domain.club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT c FROM CLUB c WHERE c.category.id = :categoryId")
    List<Club> findByCategoryId(long categoryId);

    List<Club> findByClubNmContainingOrBriefDescriptionContaining(String clubNm, String briefDescription);
}
