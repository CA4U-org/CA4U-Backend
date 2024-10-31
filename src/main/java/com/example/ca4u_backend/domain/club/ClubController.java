package com.example.ca4u_backend.domain.club;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.domain.club.dto.ClubReponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Club API", description = "클럽(동아리,학회)에 대한 요청을 담당하는 API입니다.")
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
@RestController
public class ClubController {
    private final ClubService ClubService;

    @Operation(summary = "길드 상세 조회", description = "길드의 상세페이지 정보를 전달합니다.", parameters = {
            @Parameter(name = "guildId", description = "길드아이디", in = ParameterIn.PATH)
    })
    @GetMapping("/{clubId}")
    public ApiResponse<ClubReponseDto> getClub(@PathVariable long clubId){
        return ApiResponse.ok(ClubService.getClubSpec(clubId), "클럽 상세 정보를 불러왔습니다.");
    }
}
