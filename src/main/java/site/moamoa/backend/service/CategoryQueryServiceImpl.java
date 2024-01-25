package site.moamoa.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.exception.CategoryNotFoundException;
import site.moamoa.backend.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

  @Override
  public Category findCategoryById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(
            () -> new CategoryNotFoundException()
        );
  }
}
