package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.web.dto.base.KeywordDTO;
import site.moamoa.backend.web.dto.response.KeywordResponseDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

import java.time.LocalDateTime;

import java.util.List;

public class KeywordConverter {

    public static KeywordResponseDTO.GetKeywords toGetKeywords(List<KeywordDTO> keywordDTOList) {
        return KeywordResponseDTO.GetKeywords.builder()
                .keywords(keywordDTOList)
                .build();
    }

    public static KeywordDTO toKeywordDTO(String keyword) {
        return KeywordDTO.builder()
                .keyword(keyword)
                .build();
    }

    public static KeywordResponseDTO.DeleteKeywordResult toDeleteKeywordResult(Long memberId, LocalDateTime deletedTime) {
        return KeywordResponseDTO.DeleteKeywordResult.builder()
                .userId(memberId)
                .deletedAt(deletedTime)
                .build();
    }

}
