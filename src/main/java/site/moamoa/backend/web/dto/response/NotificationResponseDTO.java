package site.moamoa.backend.web.dto.response;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.NoticeDTO;
import site.moamoa.backend.web.dto.base.NotificationDTO;

public class NotificationResponseDTO {
    @Builder
    public record GetNotification (
            NotificationDTO notificationDTO
    ) {
    }
}
