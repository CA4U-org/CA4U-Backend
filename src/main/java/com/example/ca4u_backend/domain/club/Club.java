package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.base.BaseEntity;
import com.example.ca4u_backend.domain.category.Category;
import com.example.ca4u_backend.domain.hashtag.Hashtag;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "CLUB")
public class Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id")
    private Category category;

    //여러개 썸네일 사진 (일단 하나만 할까~)
  /*  @OneToMany(mappedBy = "club")
    private List<clubThumbnail> clubThumbnailList = new ArrayList<>();*/

    @OneToMany(mappedBy = "club")
    private List<Hashtag> HashtagList = new ArrayList<>();

    //클럽명
    @Column(name = "club_nm")
    private String clubNm;

    //클럽간략설명
    @Column(name = "brief_description")
    private String briefDescription;

    //모집공고글
    @Column(name = "recruit_description")
    private String recruitDescription;

    //선발대상설명
    @Column(name = "target_people_description")
    private String targetPeopleDescription;

    //선발주기설명
    @Column(name = "target_cycle_description")
    private String targetCycleDescription;

    //지원방법설명
    @Column(name = "apply_description")
    private String applyDescription;

    @Column(name = "act_day_description")
    private String actDayDescription; //활동요일설명

    @Column(name = "location_description")
    private String locationDescription; //활동장소_설명 (동아리방 위치 등)

    @Column(name = "cost_description") // 회비 설명
    private String costDescription; // 회비 설명

    @Column(name = "spec_description") // 클럽 상세 설명 (About 클럽)
    private String specDescription; // 클럽 상세 설명 (About 클럽)

    @Column(name = "logo_img_url") // 클럽 로고 이미지 주소
    private String logoImgUrl; // 클럽 로고 이미지 주소
}
