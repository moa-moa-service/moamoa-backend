package site.moamoa.backend.service.component.query.category;

import site.moamoa.backend.domain.Category;

public interface CategoryQueryService {
    Category findCategoryById(Long id);
}
