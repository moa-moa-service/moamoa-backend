package site.moamoa.backend.web.dto.response;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.NoticeDTO;

import java.time.LocalDateTime;

public class NoticeResponseDTO {

    @Builder
    public record GetNotice(
        NoticeDTO noticeDTO,
        String author
    ) {
    }

    @Builder
    public record AddNoticeResult(
            Long noticeId,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record GetSimpleNotice(
            Long noticeId,
            String title,
            String content,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record DeleteNoticeResult(
            Long noticeId,
            LocalDateTime deletedAt
    ){
    }

    @Builder
    public record UpdateNoticeResult(
            Long noticeId,
            LocalDateTime createdAt
    ) {
    }

}
