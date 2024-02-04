package site.moamoa.backend.service.component.query.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.KeywordConverter;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.redis.RedisModuleService;
import site.moamoa.backend.web.dto.response.KeywordResponseDTO;

import java.util.Objects;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordQueryServiceImpl implements KeywordQueryService {

    private final MemberModuleService memberModuleService;
    private final RedisModuleService redisModuleService;

    //동네 인기 검색어 리스트 1위~10위까지 (조회수 기준)
    @Override
    public KeywordResponseDTO.GetKeywords popularSearchRankList(Long memberId) {
        String townName = memberModuleService.findMemberById(memberId).getTown();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisModuleService.getKeywordByTownRanking(townName);

        return KeywordConverter.toGetKeywords(
                Objects.requireNonNull(typedTuples).stream().map(typedTuple -> KeywordConverter.toKeywordDTO(typedTuple.getValue())).toList());
    }

    //개인 최근 검색어 리스트 1위~10위까지
    @Override
    public KeywordResponseDTO.GetKeywords recentSearchRankList(Long memberId) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisModuleService.getKeywordByMemberRecent(memberId);

        return KeywordConverter.toGetKeywords(
                Objects.requireNonNull(typedTuples).stream().map(typedTuple -> KeywordConverter.toKeywordDTO(typedTuple.getValue())).toList());
    }
}
