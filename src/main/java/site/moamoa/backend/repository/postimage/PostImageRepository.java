package site.moamoa.backend.repository.postimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.mapping.PostImage;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long postId);

    @Modifying
    @Query("DELETE FROM PostImage pi WHERE pi.post.id IN :postIds")
    void nullifyPostInPostImages(@Param("postIds") List<Long> postIds);
}
