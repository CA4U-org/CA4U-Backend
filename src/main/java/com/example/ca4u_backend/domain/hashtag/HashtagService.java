package com.example.ca4u_backend.domain.hashtag;

import com.example.ca4u_backend.domain.hashtag.dto.HashtagResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class HashtagService {
  private final HashtagRepository hashtagRepository;

  public List<HashtagResponseDto> getClubAllHashtags(long clubId) {
    List<Hashtag> hashtags = hashtagRepository.findAllByClubId(clubId);
    return hashtags.stream().map(HashtagResponseDto::of).toList();
  }
}
