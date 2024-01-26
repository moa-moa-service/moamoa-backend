package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.KeywordDTO;

public class KeywordResponseDTO {

    @Builder
    public record GetKeywords(
            List<KeywordDTO> keywords
    ) {
    }

    @Builder
    public record DeleteKeywordResult(
            Long userId,
            LocalDateTime deletedAt
    ) {
    }
}
