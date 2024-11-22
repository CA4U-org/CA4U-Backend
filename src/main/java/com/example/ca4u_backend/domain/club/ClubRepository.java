package com.example.ca4u_backend.domain.club;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubRepository extends JpaRepository<Club, Long> {
  @Query("SELECT c FROM CLUB c WHERE c.category.id = :categoryId")
  List<Club> findByCategoryId(long categoryId);

  List<Club> findByClubNmContainingOrBriefDescriptionContaining(
      String clubNm, String briefDescription);

  List<Club> findByIdIn(Collection<Long> clubIds);
}
