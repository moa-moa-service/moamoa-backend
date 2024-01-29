package site.moamoa.backend.service.module.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.repository.post.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostModuleServiceImpl implements PostModuleService {

    private final PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
    }

    @Override
    public List<Post> findPostsByNear(Double latitude, Double longitude) {
        return postRepository.findAllByNear(latitude, longitude);
    }

    @Override
    public List<Post> findPostsByRecent() {
        return postRepository.findAllByRecent();
    }

    @Override
    public List<Post> findPostsByRanking(String town) {
        return postRepository.findAllByRanking(town);
    }

    @Override
    public List<Post> findPostsByKeyword(String keyword) {
        return postRepository.findAllByKeyword(keyword);
    }

    @Override
    public List<Post> findPostsByKeyword(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice) {
        return postRepository.findAllByKeywordAndCondition(keyword, categoryId, dDay, total, minPrice, maxPrice);
    }
}
