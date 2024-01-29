package site.moamoa.backend.service.module.post_image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.repository.postimage.PostImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageModuleServiceImpl implements PostImageModuleService{

    private final PostImageRepository postImageRepository;

    @Override
    public void deletePostImageByPostId(Long postId) {
        List<PostImage> postImages = postImageRepository.findByPostId(postId);
        postImageRepository.deleteAll(postImages);
    }
}
