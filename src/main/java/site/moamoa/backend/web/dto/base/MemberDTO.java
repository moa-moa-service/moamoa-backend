package site.moamoa.backend.web.dto.base;

import lombok.Builder;

@Builder
public record MemberDTO(
        String nickname,
        String profileImage,
        String townName
) {
}
