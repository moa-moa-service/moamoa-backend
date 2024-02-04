package site.moamoa.backend.web.dto.response;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.NoticeDTO;

import java.time.LocalDateTime;

public class NoticeResponseDTO {

    @Builder
    public record GetNotice(
            NoticeDTO noticeDTO
    ) {
    }

    @Builder
    public record AddNoticeResult(
            Long noticeId,
            LocalDateTime createdAt
    ) {
    }
}
