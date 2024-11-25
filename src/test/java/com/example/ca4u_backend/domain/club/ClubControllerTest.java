package com.example.ca4u_backend.domain.club;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs")
public class ClubControllerTest {
  @GetMapping("/test")
  public ResponseEntity<String> testEndPoint() {
    return ResponseEntity.ok("test success");
  }
}
