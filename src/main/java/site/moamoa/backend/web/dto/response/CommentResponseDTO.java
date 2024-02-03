package site.moamoa.backend.web.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class CommentResponseDTO {

    @Builder
    public record AddCommentResult(
            Long commentId,
            LocalDateTime createdAt
    ) {
    }
}
