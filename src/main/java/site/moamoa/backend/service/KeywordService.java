package site.moamoa.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import site.moamoa.backend.web.dto.base.KeywordDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final RedisTemplate<String, String> redisTemplate;

    //동네 인기 검색어 리스트 1위~10위까지 (조회수 기준)
    public List<KeywordDTO> PopularSearcRankList(String townName) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores("town::"+ townName, 0, 9);  //score순으로 10개 보여줌
        List<KeywordDTO> keywordDTOList = typedTuples.stream()
                .map(typedTuple -> KeywordDTO.builder()
                        .keyword(typedTuple.getValue())
                        .build())
                .collect(Collectors.toList());
        return keywordDTOList;
    }

    //개인 최근 검색어 리스트 1위~10위까지
    public List<KeywordDTO> RecentSearchRankList(Long memberId) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores("member::"+memberId, 0, 9);  //score순으로 10개 보여줌
        List<KeywordDTO> keywordDTOList = typedTuples.stream()
                .map(typedTuple -> KeywordDTO.builder()
                        .keyword(typedTuple.getValue())
                        .build())
                .collect(Collectors.toList());
        return keywordDTOList;
    }
}
