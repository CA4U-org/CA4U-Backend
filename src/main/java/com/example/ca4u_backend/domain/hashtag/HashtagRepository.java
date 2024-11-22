package com.example.ca4u_backend.domain.hashtag;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
  @Query("SELECT h FROM HASHTAG h WHERE h.club.id = :clubId")
  List<Hashtag> findAllByClubId(long clubId);
}
