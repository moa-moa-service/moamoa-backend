package site.moamoa.backend.service.keyword.query;

import site.moamoa.backend.web.dto.base.KeywordDTO;

import java.util.List;

public interface KeywordQueryService {
    //동네 인기 검색어 리스트 1위~10위까지 (조회수 기준)
    List<KeywordDTO> popularSearchRankList(String townName);

    //개인 최근 검색어 리스트 1위~10위까지
    List<KeywordDTO> recentSearchRankList(Long memberId);
}
