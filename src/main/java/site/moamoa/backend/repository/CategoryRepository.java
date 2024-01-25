package site.moamoa.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
