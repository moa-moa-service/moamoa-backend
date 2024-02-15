package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.domain.enums.NotificationStatus;
import site.moamoa.backend.domain.enums.NotificationType;
import site.moamoa.backend.web.dto.base.NotificationDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.NotificationResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

public class NotificationConverter {
    public static NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder()
                .notificationId(notification.getId())
                .message(notification.getMessage())
                .type(notification.getType())
                .status(notification.getStatus())
                .build();
    }

    public static NotificationResponseDTO.GetNotification toGetNotification(NotificationDTO notificationDTO) {
        return NotificationResponseDTO.GetNotification.builder()
                .notificationDTO(notificationDTO)
                .build();
    }

    public static PostResponseDTO.GetPosts toGetPosts(List<SimplePostDTO> dtoList) {
        return PostResponseDTO.GetPosts.builder()
                .SimplePostDtoList(dtoList)
                .build();
    }
}
