package site.moamoa.backend.repository.postimage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.mapping.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long postId);
}
