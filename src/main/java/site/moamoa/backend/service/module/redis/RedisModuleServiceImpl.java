package site.moamoa.backend.service.module.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.KeywordHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisModuleServiceImpl implements RedisModuleService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> redisZSetTemplate;

    // Redis key
    private static final String POST_VIEW_KEY_PREFIX = "postView:";
    private static final String MEMBER_KEYWORD_KEY_PREFIX = "memberKeyword:";
    private static final String TOWN_KEYWORD_COUNT_KEY_PREFIX = "townKeywordCount:";

    // Redis Expire Time
    private static final Long EXPIRATION_VIEW_RECORD = 24 * 60 * 60L;  // 1 Day

    // Redis Set Size
    private static final Long RECENT_KEYWORD_SIZE = 10L;

    @Override
    public void addKeywordToMemberRecent(Long memberId, String keyword) {
        String memberKey = MEMBER_KEYWORD_KEY_PREFIX + memberId;
        redisTemplate.opsForZSet().add(memberKey, keyword, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        Long size = redisTemplate.opsForZSet().size(memberKey);
        if (size != null && size > RECENT_KEYWORD_SIZE) {
            redisTemplate.opsForZSet().popMin(memberKey);
        }
    }

    @Override
    public void increaseTownKeywordCount(String town, String keyword) {
        String townKey = TOWN_KEYWORD_COUNT_KEY_PREFIX + town;

        redisTemplate.opsForZSet().addIfAbsent(townKey, keyword, 0);
        redisTemplate.opsForZSet().incrementScore(townKey, keyword, 1);
    }

    @Override
    public boolean isNewPostViewRecord(Long memberId, Long postId) {
        String key = POST_VIEW_KEY_PREFIX + memberId + ":" + postId;
        if (redisTemplate.opsForValue().get(key) == null) {
            savePostViewRecord(key);
            return true;
        }
        return false;
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getKeywordByTownRanking(String townName) {
        return redisZSetTemplate.opsForZSet().reverseRangeWithScores(TOWN_KEYWORD_COUNT_KEY_PREFIX + townName, 0, 9);  //score순으로 10개 보여줌
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getKeywordByMemberRecent(Long memberId) {
        return redisZSetTemplate.opsForZSet().reverseRangeWithScores(MEMBER_KEYWORD_KEY_PREFIX + memberId, 0, 9);  //score순으로 10개 보여줌
    }

    @Override
    public void deleteKeywordByMemberRecent(Long memberId, String keyword) {
        String key = MEMBER_KEYWORD_KEY_PREFIX + memberId;

        if (Objects.requireNonNull(redisZSetTemplate.opsForZSet().remove(key, keyword)) == 0L) {
            throw new KeywordHandler(ErrorStatus.KEYWORD_NOT_FOUND);
        }
    }

    @Override
    public String getKeywordByMemberRecentFirst(Long memberId) {
        return Objects.requireNonNull(redisZSetTemplate.opsForZSet()
                        .range(MEMBER_KEYWORD_KEY_PREFIX + memberId, 0, 0)).stream()
                .findFirst().orElse(null);
    }

    private void savePostViewRecord(String key) {
        redisTemplate.opsForValue().set(key, "read");
        redisTemplate.expire(key, Duration.ofSeconds(EXPIRATION_VIEW_RECORD));
    }

}