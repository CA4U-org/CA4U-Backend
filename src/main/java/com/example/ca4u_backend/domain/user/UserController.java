package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.common.auth.Auth;
import com.example.ca4u_backend.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/users/me")
    public UserResponseDto getUser(@Auth Long userId) {
        return userService.getUser(userId);
    }
}
