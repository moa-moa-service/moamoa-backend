package site.moamoa.backend.service.module.post_image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.converter.PostImageConverter;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.repository.postimage.PostImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageModuleServiceImpl implements PostImageModuleService {

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

    @Override
    public List<PostImage> setUpdatedImages(List<MultipartFile> images, Post updatePost) {
        List<PostImage> postImages = PostImageConverter.toPostImages(images, amazonS3Manager);
        postImages.forEach(postImage -> postImage.setPost(updatePost));
        return postImages;
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> imageIds) {
        postImageRepository.deleteAllByIdInBatch(imageIds);
    }
}
