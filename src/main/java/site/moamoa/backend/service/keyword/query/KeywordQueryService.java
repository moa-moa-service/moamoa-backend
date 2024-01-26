package site.moamoa.backend.service.keyword.query;

import site.moamoa.backend.web.dto.response.KeywordResponseDTO;

public interface KeywordQueryService {
    //동네 인기 검색어 리스트 1위~10위까지 (조회수 기준)
    KeywordResponseDTO.GetKeywords popularSearchRankList(String townName);

    //개인 최근 검색어 리스트 1위~10위까지
    KeywordResponseDTO.GetKeywords recentSearchRankList(Long memberId);

    //개인 최근 검색어 중 삭제
    KeywordResponseDTO.DeleteKeywordResult deleteRecentKeyword(Long memberId, String keyword);

}
