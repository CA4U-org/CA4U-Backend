package com.example.ca4u_backend.domain.hashtag;

import com.example.ca4u_backend.base.BaseEntity;
import com.example.ca4u_backend.domain.club.Club;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "HASHTAG")
public class Hashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hashtag_nm", nullable = false)
    private String hashtagNm;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
