package com.example.ca4u_backend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM USER u WHERE u.username = :username")
  User findByUsername(String username);
}
