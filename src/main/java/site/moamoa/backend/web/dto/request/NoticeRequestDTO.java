package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.NotNull;

public class NoticeRequestDTO {

    public record AddNotice(
            @NotNull
            String title,
            @NotNull
            String content
    ) {
    }

}
