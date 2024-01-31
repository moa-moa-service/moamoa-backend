package site.moamoa.backend.service.module.post_image;

import java.util.List;
import site.moamoa.backend.domain.mapping.PostImage;

public interface PostImageModuleService {
    void deletePostImageByPostId(Long postId);

    List<PostImage> findPostImageByPostId(Long postId);
}
