package site.moamoa.backend.web.dto.base;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CommentDTO(
        Long commentId,
        Long parentCommentId,
        String nickname,
        List<CommentDTO> childrenCommentDtoList,
        String content,
        LocalDateTime createdAt
) {
}
