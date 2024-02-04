package site.moamoa.backend.repository.postimage;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.mapping.PostImage;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long postId);
}
