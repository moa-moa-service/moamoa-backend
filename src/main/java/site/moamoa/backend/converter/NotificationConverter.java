package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.web.dto.base.NotificationDTO;
import site.moamoa.backend.web.dto.response.NotificationResponseDTO;

import java.util.List;

public class NotificationConverter {
    public static NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder()
                .notificationId(notification.getId())
                .message(notification.getMessage())
                .type(notification.getType().getBelongingTo())
                .referenceId(notification.getReferenceId())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public static NotificationResponseDTO.GetNotifications toGetNotifications(List<NotificationDTO> dtoList) {
        return NotificationResponseDTO.GetNotifications.builder()
                .notificationDTOList(dtoList)
                .build();
    }
}
