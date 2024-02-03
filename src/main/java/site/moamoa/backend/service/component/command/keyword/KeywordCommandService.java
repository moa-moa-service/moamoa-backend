package site.moamoa.backend.service.component.command.keyword;

import site.moamoa.backend.web.dto.response.KeywordResponseDTO;

public interface KeywordCommandService {
    //개인 최근 검색어 중 삭제
    KeywordResponseDTO.DeleteKeywordResult deleteMemberKeyword(Long memberId, String keyword);

    void addMemberKeyword(Long memberId, String keyword);

    void updateTownKeywordCount(Long memberId, String keyword);
}
