package site.moamoa.backend.service.module.redis;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Optional;
import java.util.Set;

public interface RedisModuleService {

    void addKeywordToMemberRecent(Long memberId, String keyword);

    void increaseTownKeywordCount(String town, String keyword);

    boolean isNewPostViewRecord(Long memberId, Long postId);

    Set<ZSetOperations.TypedTuple<String>> getKeywordByTownRanking(String townName);

    Set<ZSetOperations.TypedTuple<String>> getKeywordByMemberRecent(Long memberId);

    void deleteKeywordByMemberRecent(Long memberId, String keyword);

    String getKeywordByMemberRecentFirst(Long memberId);

    void expireAccessToken(String accessToken, Long expiration);

    Optional<String> getLogoutStatus(String accessToken);

    void checkHealth();
}
