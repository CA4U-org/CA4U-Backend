package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.common.auth.Auth;
import com.example.ca4u_backend.domain.club.dto.ClubResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club API", description = "클럽(동아리,학회)에 대한 요청을 담당하는 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ClubController {
  private final ClubService clubService;

  @Operation(
      summary = "클럽 상세 조회",
      description = "클럽의 상세페이지 정보를 전달합니다.",
      parameters = {@Parameter(name = "clubId", description = "클럽 아이디", in = ParameterIn.PATH)})
  @GetMapping("/clubs/{clubId}")
  public ApiResponse<ClubResponseDto> getClub(@PathVariable long clubId) {
    return ApiResponse.ok(clubService.getClubSpec(clubId), "클럽 상세 정보를 불러왔습니다.");
  }

  @Operation(
      summary = "카테고리에 속한 클럽 조회",
      description = "특정 카테고리에 속한 클럽들을 카테고리 아이디로 조회합니다.",
      parameters = {
        @Parameter(name = "categoryId", description = "카테고리 아이디", in = ParameterIn.PATH)
      })
  @GetMapping("/categories/{categoryId}/clubs")
  public ApiResponse<List<ClubResponseDto>> getClubsByCategory(@PathVariable long categoryId) {
    return ApiResponse.ok(clubService.getClubsByCategoryId(categoryId), "카테고리에 속한 클럽들을 불러왔습니다.");
  }

  @Operation(
      summary = "클럽 리스트 조회",
      description = "검색어가 포함된 동아리 목록을 조회합니다.",
      parameters = {@Parameter(name = "search", description = "검색어", in = ParameterIn.QUERY)})
  @GetMapping("/clubs")
  public ApiResponse<List<ClubResponseDto>> getClubsBySearch(
      @Parameter(description = "검색어") String search) {
    if (StringUtils.hasText(search)) {
      return ApiResponse.ok(Collections.emptyList(), "검색어가 제공되지 않았습니다.");
    }
    return ApiResponse.ok(clubService.getClubsBySearch(search), "클럽 리스트를 불러왔습니다.");
  }

  @GetMapping("/clubs/content-recommendation")
  public ApiResponse<List<ClubResponseDto>> getRecommendedClubsByContent(
      @RequestParam List<Long> clubIds) {
    return ApiResponse.ok(clubService.getRelatedClubs(clubIds), "추천 클럽 리스트를 불러왔습니다.");
  }

  @GetMapping("/clubs/user-recommendation")
  public ApiResponse<List<ClubResponseDto>> getUserRecommendedClubs(@Auth Long userId) {
    return ApiResponse.ok(
        clubService.getRecommendedClubsByUser(userId), "사용자에게 추천된 클럽 리스트를 불러왔습니다.");
  }
}
