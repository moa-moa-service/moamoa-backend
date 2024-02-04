package site.moamoa.backend.web.dto.base;

import lombok.Builder;

import java.util.List;

@Builder
public record NoticeDTO(
        Long noticeId,
        Long postId,
        String imageUrl,
        String content,
        List<CommentDTO> commentDTOList
) {
}
