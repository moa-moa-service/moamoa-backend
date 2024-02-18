package site.moamoa.backend.service.module.post_image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.PostImage;

public interface PostImageModuleService {
    void deletePostImageByPostId(Long postId);

    List<PostImage> findPostImageByPostId(Long postId);

    List<PostImage> setUpdatedImages(List<MultipartFile> images, Post updatePost);

    void nullifyPostInPostImages(List<Long> imageIds);
}
