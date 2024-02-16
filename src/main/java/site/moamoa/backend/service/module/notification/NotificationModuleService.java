package site.moamoa.backend.service.module.notification;

import site.moamoa.backend.domain.Notification;

import java.util.List;

public interface NotificationModuleService {
    void saveNotification(Notification notification);
    void saveAllNotifications(List<Notification> notificationList);
    List<Notification> findNotificationsByMemberId(Long memberId);
}
