package site.moamoa.backend.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
