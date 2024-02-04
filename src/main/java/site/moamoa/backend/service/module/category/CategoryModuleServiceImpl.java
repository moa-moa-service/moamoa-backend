package site.moamoa.backend.service.module.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.CategoryHandler;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.repository.category.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryModuleServiceImpl implements CategoryModuleService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));
    }
}