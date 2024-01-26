package site.moamoa.backend.service.category.query;

import site.moamoa.backend.domain.Category;

public interface CategoryQueryService {
    Category findCategoryById(Long id);
}
