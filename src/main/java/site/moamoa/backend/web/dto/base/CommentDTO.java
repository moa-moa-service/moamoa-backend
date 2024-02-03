package site.moamoa.backend.web.dto.base;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentDTO(
        Long commentId,
        Long memberId,
        List<CommentDTO> childrenCommentDtoList,
        CommentDTO parentCommentDto,
        String content
) {
}
