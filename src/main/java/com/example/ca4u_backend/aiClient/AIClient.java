package com.example.ca4u_backend.aiClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AIClient {

    private final RestTemplate restTemplate;
    private static final String ADDRESS = "http://localhost:8000";

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ContentRecommendationResponse {
        @JsonProperty("recommended_club")
        private List<ContentRecommendationItem> recommendedClub;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ContentRecommendationItem {
        private Long id;
        private String name;
    }

    public Set<Long> recommendedByContent(Long clubId) {
        Set<Long> recommendedClubIds = new HashSet<>();
        ResponseEntity<ContentRecommendationResponse> resp = restTemplate.getForEntity(ADDRESS + "/clubs/content/recommend/" + clubId, ContentRecommendationResponse.class);
        resp.getBody().getRecommendedClub().forEach(recommendedClub -> recommendedClubIds.add(recommendedClub.getId()));
        return recommendedClubIds;
    }

    public Set<Long> recommendByContent(List<Long> clubIds) {
        Set<Long> recommendedClubIds = new HashSet<>();
        for (Long clubId : clubIds) {
            recommendedClubIds.addAll(recommendedByContent(clubId));
        }
        return recommendedClubIds;
    }
}
