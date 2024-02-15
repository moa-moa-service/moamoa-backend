package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import site.moamoa.backend.domain.enums.NotificationStatus;

import java.time.LocalDateTime;


@Builder
public record NotificationDTO(
    Long notificationId,
    String message,
    String type,
    Long referenceId,
    NotificationStatus status,
    LocalDateTime createdAt
) {
}
