package com.example.ca4u_backend.domain.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    @Query("SELECT h FROM HASHTAG h WHERE h.club.id = :clubId")
    List<Hashtag> findAllByClubId(long clubId);

}
