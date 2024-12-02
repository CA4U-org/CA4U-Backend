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

  @Query(
      value =
          """
    SELECT
        cl.id AS collegeId,
        cl.name AS collegeName,
        m.id AS majorId,
        m.name AS majorName
    FROM
        COLLEGE cl
    JOIN
        MAJOR m ON cl.id = m.college_id
    ORDER BY
        cl.id, m.id
    """,
      nativeQuery = true)
  List<Object[]> findCollegesWithMajorsNative();
}
