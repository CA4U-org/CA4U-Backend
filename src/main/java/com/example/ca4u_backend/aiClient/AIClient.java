package com.example.ca4u_backend.aiClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIClient {

    private final RestTemplate restTemplate;
    private static final String ADDRESS = "http://localhost:8000";

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ContentRecommendationResponse {
        @JsonProperty("recommended_clubs")
        private List<ContentRecommendationItem> recommendedClubs;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ContentRecommendationItem {
        private Long id;
        private String name;
    }

    public Set<Long> recommendedByContent(List<Long> clubId) {
        String query = clubId.stream().map(String::valueOf).collect(Collectors.joining(","));
        Set<Long> recommendedClubIds = new HashSet<>();
        ResponseEntity<ContentRecommendationResponse> resp = restTemplate.getForEntity(ADDRESS + "/clubs/content/recommend/n/" + query, ContentRecommendationResponse.class);
        resp.getBody().getRecommendedClubs().forEach(recommendedClub -> recommendedClubIds.add(recommendedClub.getId()));
        return recommendedClubIds;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UserRecommendationResponse {
        @JsonProperty("recommended_club")
        private List<ContentRecommendationItem> recommendedClubs;
    }

    public Set<Long> recommendedByUser(Long userId) {
        try {
            Set<Long> recommendedClubIds = new HashSet<>();
            ResponseEntity<UserRecommendationResponse> resp = restTemplate.getForEntity(ADDRESS + "/clubs/user/recommend/" + userId, UserRecommendationResponse.class);
            resp.getBody().getRecommendedClubs().forEach(recommendedClub -> recommendedClubIds.add(recommendedClub.getId()));
            return recommendedClubIds;
        } catch (Exception e) {
            log.error("Error occurs while sending request at ai app, {} ", e.getMessage());
            return Collections.emptySet();
        }
    }
}
