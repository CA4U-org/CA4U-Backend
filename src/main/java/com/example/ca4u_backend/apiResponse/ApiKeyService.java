package com.example.ca4u_backend.apiResponse;

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

    if (!key.isActive()) {
      return false;
    }

    // 만기시 삭제
    if (key.isExpired()) {
      apiKeyRepository.delete(key);
      return false;
    }

    return true; // 모든 조건을 만족하면 유효
  }
}
