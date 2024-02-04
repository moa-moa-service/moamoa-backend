package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.NotNull;

public class CommentRequestDTO {

    public record AddComment(
            Long parentId,
            @NotNull
            String content
    ) {
    }
}
