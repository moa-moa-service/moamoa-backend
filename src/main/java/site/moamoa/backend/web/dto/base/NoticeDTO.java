package site.moamoa.backend.web.dto.base;

import java.time.LocalDateTime;
import lombok.Builder;

import java.util.List;

@Builder
public record NoticeDTO(
        Long noticeId,
        Long postId,
        String title,
        String imageUrl,
        String content,
        List<CommentDTO> commentDTOList,
        LocalDateTime createdAt
) {
}
