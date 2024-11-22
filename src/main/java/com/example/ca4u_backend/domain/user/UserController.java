package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.common.auth.Auth;
import com.example.ca4u_backend.domain.user.dto.UpdateStudentInfoRequest;
import com.example.ca4u_backend.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/api/v1/users/me")
  public ApiResponse<UserResponseDto> getMe(@Auth(required = true) Long userId) {
    return ApiResponse.ok(userService.getUser(userId));
  }

  @PatchMapping("/api/v1/users/me")
  public void updateMe(
      @Auth(required = true) Long userId, @RequestBody UpdateStudentInfoRequest requestDto) {
    userService.updateUser(userId, requestDto);
  }
}
