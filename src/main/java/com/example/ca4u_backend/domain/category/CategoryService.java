package com.example.ca4u_backend.domain.category;

import com.example.ca4u_backend.domain.category.dto.CategoryResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public List<CategoryResponseDto> getAllCategories() {
    return categoryRepository.findAll().stream().map(CategoryResponseDto::of).toList();
  }
}
