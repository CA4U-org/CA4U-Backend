package com.example.ca4u_backend.apiResponse;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApiKeyService {
  private final ApiKeyRepository apiKeyRepository;

  public Boolean isValid(String apiKey) {
    Optional<ApiKey> apiKeyEntity = apiKeyRepository.findByApiKey(apiKey);

    // API 키가 DB에 없으면 무효
    if (apiKeyEntity.isEmpty()) {
      return false;
    }

    ApiKey key = apiKeyEntity.get();

    // API 키가 만료되었거나 비활성화되면 무효
    if (isExpired(key) || !isActive(key)) {
      return false;
    }

    return true; // 모든 조건을 만족하면 유효
  }

  private Boolean isExpired(ApiKey apiKey) {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(apiKey.getExpiresAt());
  }

  private Boolean isActive(ApiKey apiKey) {
    return apiKey.getIsActive();
  }
}
