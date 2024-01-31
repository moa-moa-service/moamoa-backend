package site.moamoa.backend.service.module.post_image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.repository.postimage.PostImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageModuleServiceImpl implements PostImageModuleService{

    private final PostImageRepository postImageRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public void deletePostImageByPostId(Long postId) {
        List<PostImage> postImages = findPostImageByPostId(postId);
        postImages.stream()
            .map(deletedPostImage -> amazonS3Manager.extractImageNameFromUrl(deletedPostImage.getUrl()))
            .forEach(amazonS3Manager::deleteImage);
        postImageRepository.deleteAll(postImages);
    }

    @Override
    public List<PostImage> findPostImageByPostId(Long postId) {
        return postImageRepository.findByPostId(postId);
    }
}
