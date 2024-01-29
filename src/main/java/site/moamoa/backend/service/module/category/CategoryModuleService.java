package site.moamoa.backend.service.module.category;

import site.moamoa.backend.domain.Category;

public interface CategoryModuleService {
    Category findCategoryById(Long categoryId);
}
