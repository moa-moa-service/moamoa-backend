package site.moamoa.backend.service.post.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.config.redis.RedisKey;
import site.moamoa.backend.repository.post.PostRepository;
import site.moamoa.backend.service.member.query.MemberQueryService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    private final MemberQueryService memberQueryService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void updateKeywordCount(Long memberId, String keyword) {
        redisTemplate.opsForZSet()
                .add(RedisKey.MEMBER_KEYWORD_KEY_PREFIX + memberId, keyword, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        String town = memberQueryService.findMemberById(memberId).getTown();
        redisTemplate.opsForZSet().addIfAbsent(RedisKey.TOWN_KEYWORD_COUNT_KEY_PREFIX + town, keyword, 0);
        redisTemplate.opsForZSet().incrementScore(RedisKey.TOWN_KEYWORD_COUNT_KEY_PREFIX + town, keyword, 1);
    }

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
        return RedisKey.POST_VIEW_KEY_PREFIX + memberId + ":" + postId;
    }

    private boolean isNewViewRecord(Long memberId, Long postId) {
        return Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(buildPostViewKey(memberId, postId), postId));
    }

    private void saveViewRecord(String key) {
        redisTemplate.opsForSet().add(key, true);
        redisTemplate.expire(key, Duration.ofSeconds(RedisKey.EXPIRATION_VIEW_RECORD));
    }

    private void updatePostView(Long postId) {
        if (postRepository.updateView(postId) != 1) {
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        }
    }


}
