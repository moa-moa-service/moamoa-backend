package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import site.moamoa.backend.domain.enums.NotificationStatus;
import site.moamoa.backend.domain.enums.NotificationType;


@Builder
public record NotificationDTO(
    Long notificationId,
    String message,
    NotificationType type,
    NotificationStatus status
) {
}
