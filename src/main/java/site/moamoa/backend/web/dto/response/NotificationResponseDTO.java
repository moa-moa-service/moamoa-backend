package site.moamoa.backend.web.dto.response;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.NotificationDTO;

import java.util.List;

public class NotificationResponseDTO {
    @Builder
    public record GetNotifications (
            List<NotificationDTO> notificationDTOList
    ) {
    }
}
