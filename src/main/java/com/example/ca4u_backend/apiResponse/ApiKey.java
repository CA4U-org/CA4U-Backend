package com.example.ca4u_backend.apiResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Entity(name = "API_KEY")
public class ApiKey {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "client_name", nullable = false)
  private String clientName;

  @Column(name = "api_key", unique = true, nullable = false)
  private String apiKey;

  @CreatedDate
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

  @Column(name = "is_active")
  private Boolean isActive;

  @PrePersist
  public void prePersist() {
    this.createdAt = this.createdAt == null ? LocalDateTime.now() : this.createdAt;
    this.expiresAt = this.expiresAt == null ? this.createdAt.plusMonths(1) : this.expiresAt;
  }

  public Boolean isExpired() {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(this.expiresAt);
  }

  public Boolean isActive() {
    return this.isActive;
  }
}
