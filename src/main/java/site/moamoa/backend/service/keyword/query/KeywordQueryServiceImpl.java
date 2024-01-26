package site.moamoa.backend.service.keyword.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.config.redis.RedisKey;
import site.moamoa.backend.converter.KeywordConverter;
import site.moamoa.backend.web.dto.response.KeywordResponseDTO;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordQueryServiceImpl implements KeywordQueryService {

    private final RedisTemplate<String, String> redisTemplate;

    //동네 인기 검색어 리스트 1위~10위까지 (조회수 기준)
    @Override
    public KeywordResponseDTO.GetKeywords popularSearchRankList(String townName) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOperations.reverseRangeWithScores(RedisKey.TOWN_KEYWORD_COUNT_KEY_PREFIX + townName, 0, 9);  //score순으로 10개 보여줌

        return KeywordConverter.toGetKeywords(
                Objects.requireNonNull(typedTuples).stream().map(typedTuple -> KeywordConverter.toKeywordDTO(typedTuple.getValue())).toList()
        );
    }

    //개인 최근 검색어 리스트 1위~10위까지
    @Override
    public KeywordResponseDTO.GetKeywords recentSearchRankList(Long memberId) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOperations.reverseRangeWithScores(RedisKey.MEMBER_KEYWORD_KEY_PREFIX + memberId, 0, 9);  //score순으로 10개 보여줌
        return KeywordConverter.toGetKeywords(
                Objects.requireNonNull(typedTuples).stream().map(typedTuple -> KeywordConverter.toKeywordDTO(typedTuple.getValue())).toList()
        );
    }

    //개인 최근 검색어 중 삭제
    @Override
    public KeywordResponseDTO.DeleteKeywordResult deleteRecentKeyword(Long memberId, String keyword) {
        String memberKey = RedisKey.MEMBER_KEYWORD_KEY_PREFIX + memberId;
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.remove(memberKey, keyword);
        LocalDateTime deletedTime = LocalDateTime.now();
        return KeywordConverter.toDeleteKeywordResult(memberId, deletedTime);

    }
}
