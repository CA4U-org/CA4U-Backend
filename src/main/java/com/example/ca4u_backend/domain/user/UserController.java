package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.common.auth.Auth;
import com.example.ca4u_backend.domain.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/users/me")
    public ApiResponse<UserResponseDto> getMe(@Auth Long userId) {

        if (userId == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        return ApiResponse.ok(userService.getUser(userId));
    }

    @GetMapping("/user-info")
    public String getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return "Username: " + userDetails.getUsername();
            } else {
                return "Principal: " + principal.toString();
            }
        }

        return "No authenticated user found";
    }
}
