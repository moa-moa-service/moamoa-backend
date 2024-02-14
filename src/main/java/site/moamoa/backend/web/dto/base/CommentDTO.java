package site.moamoa.backend.web.dto.base;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentDTO(
        Long commentId,
        Long parentCommentId,
        Long memberId,
        List<CommentDTO> childrenCommentDtoList,
        String content
) {
}
