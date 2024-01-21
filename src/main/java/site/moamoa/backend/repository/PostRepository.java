package site.moamoa.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByProductNameContaining(String keyword);
}
