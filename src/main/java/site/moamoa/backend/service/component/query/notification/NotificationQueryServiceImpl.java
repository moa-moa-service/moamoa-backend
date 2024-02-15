package site.moamoa.backend.service.component.query.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.NotificationConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.service.module.notification.NotificationModuleService;
import site.moamoa.backend.web.dto.response.NotificationResponseDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService{
    private final NotificationModuleService notificationModuleService;

    @Override
    public List<NotificationResponseDTO.GetNotification> findNotificationByMemberId(Long memberId) {
        List<Notification> notifications = notificationModuleService.findNotificationsByMemberId(memberId);
        return notifications.stream().map(notification -> NotificationConverter.toGetNotification(NotificationConverter.toNotificationDTO(notification)))
                .toList();
    }
}
