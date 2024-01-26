package site.moamoa.backend.service.category.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.CategoryHandler;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.repository.category.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

  @Override
  public Category findCategoryById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(
            () -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND)
        );
  }
}
