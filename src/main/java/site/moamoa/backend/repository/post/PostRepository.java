package site.moamoa.backend.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDSLRepository {
    List<Post> findByProductNameContaining(String keyword);
}