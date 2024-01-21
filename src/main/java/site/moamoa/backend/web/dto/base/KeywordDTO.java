package site.moamoa.backend.web.dto.base;

import lombok.Builder;

@Builder
public record KeywordDTO(
        String keyword
) {
}
