package com.example.ca4u_backend.hc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/api/v1/health")
    public void healthCheck() {
        log.trace("Health Check");
    }
}
