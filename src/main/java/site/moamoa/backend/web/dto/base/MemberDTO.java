package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record MemberDTO(
        String nickname,
        MultipartFile profileImage,
        String townName
) {
}
