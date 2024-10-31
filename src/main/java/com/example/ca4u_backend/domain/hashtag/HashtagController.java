package com.example.ca4u_backend.domain.hashtag;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.domain.hashtag.dto.HashtagReponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Hashtag API", description = "해쉬태그 관련된 요청을 담당하는 API입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class HashtagController {
    private final HashtagService hashtagService;

    @Operation(summary = "특정클럽의 해쉬태그 목록 조회", description = "클럽의 상세페이지에서 해쉬태그 정보를 전달합니다.", parameters = {
            @Parameter(name = "clubId", description = "클럽 아이디", in = ParameterIn.PATH)
    })
    @GetMapping("/{clubId}/hashtags")
    public ApiResponse<List<HashtagReponseDto>> getClubHashtags(@PathVariable long clubId){
        return ApiResponse.ok(hashtagService.getClubAllHashtags(clubId), "클럽 상세 정보를 불러왔습니다.");
    }
}
