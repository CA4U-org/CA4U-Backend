package com.example.ca4u_backend.domain.category;

import com.example.ca4u_backend.apiResponse.ApiResponse;
import com.example.ca4u_backend.domain.category.dto.CategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리", description = "카테고리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {
  private final CategoryService categoryService;

  @Operation(summary = "카테고리 목록 조회", description = "카테고리별로 보기에 있는 6가지 카테고리 목록을 조회합니다.")
  @GetMapping("/categories")
  public ApiResponse<List<CategoryResponseDto>> getCategories() {
    return ApiResponse.ok(categoryService.getAllCategories(), "카테고리 목록 조회 성공");
  }
}
