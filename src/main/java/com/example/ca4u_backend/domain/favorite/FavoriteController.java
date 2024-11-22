package com.example.ca4u_backend.domain.favorite;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.common.auth.Auth;
import com.example.ca4u_backend.domain.club.dto.ClubResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Favorite API", description = "즐겨찾기와 관련된 요청을 담당하는 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class FavoriteController {
  private final FavoriteService favoriteService;

  @Operation(
      summary = "즐겨찾기 클럽 추가/삭제",
      description = "즐겨찾기 클럽을 추가하거나 삭제합니다.",
      parameters = {@Parameter(name = "clubId", description = "클럽 아이디", in = ParameterIn.PATH)})
  @PostMapping("/clubs/{clubId}/favorites")
  public ApiResponse<Boolean> toggleClubFavoriteStatus(
      @PathVariable long clubId, @Auth Long userId) {
    boolean isAdded = favoriteService.toggleClubFavoriteStatus(clubId, userId);
    String message = isAdded ? "즐겨찾기 클럽이 추가되었습니다." : "즐겨찾기 클럽이 삭제되었습니다.";
    return ApiResponse.ok(isAdded, message);
  }

  @Operation(summary = "사용자에 따른 즐겨찾기 목록 조회", description = "특정 사용자가 즐겨찾기한 클럽 목록을 조회합니다.")
  @GetMapping("/clubs/favorites")
  public ApiResponse<List<ClubResponseDto>> getFavoriteClubsByUser(@Auth Long userId) {
    return ApiResponse.ok(favoriteService.getFavoriteClubsByUserId(userId), "즐겨찾기한 클럽 목록을 불러왔습니다.");
  }
}
