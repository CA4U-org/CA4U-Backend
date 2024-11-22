package com.example.ca4u_backend.domain.category;

import com.example.ca4u_backend.common.entity.BaseEntity;
import com.example.ca4u_backend.domain.club.Club;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity(name = "CATEGORY")
public class Category extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "category_nm", nullable = false)
  private String categoryNm;

  @Column(name = "img_url")
  private String imgUrl;

  @OneToMany(mappedBy = "category")
  private List<Club> clubList = new ArrayList<>();
}
