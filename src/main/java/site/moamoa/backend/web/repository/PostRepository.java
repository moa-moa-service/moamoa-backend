package site.moamoa.backend.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
