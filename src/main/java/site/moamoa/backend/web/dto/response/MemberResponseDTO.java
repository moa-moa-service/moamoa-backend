package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MemberResponseDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddMemberInfoResult {
        Long member_id;

        LocalDateTime createdAt;
    }

}
