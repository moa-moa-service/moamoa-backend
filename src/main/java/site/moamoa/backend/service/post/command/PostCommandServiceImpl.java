package site.moamoa.backend.service.post.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.repository.PostRepository;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String POST_VIEW_KEY_PREFIX = "postView:";
    private static final Long EXPIRATION_VIEW_RECORD = 24 * 60 * 60L;  // 1 Day

    @Override
    public void updatePostViewCount(Long memberId, Long postId) {
        String key = buildPostViewKey(memberId, postId);

        // 이미 조회한 경우 무시
        if (isNewViewRecord(memberId, postId)) {
            saveViewRecord(key);
            updatePostView(postId);
        }
    }

    private String buildPostViewKey(Long memberId, Long postId) {
        return POST_VIEW_KEY_PREFIX + memberId + ":" + postId;
    }

    private boolean isNewViewRecord(Long memberId, Long postId) {
        return Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(buildPostViewKey(memberId, postId), postId));
    }

    private void saveViewRecord(String key) {
        redisTemplate.opsForSet().add(key, true);
        redisTemplate.expire(key, Duration.ofSeconds(EXPIRATION_VIEW_RECORD));
    }

    private void updatePostView(Long postId) {
        if (postRepository.updateView(postId) != 1) {
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        }
    }
}
