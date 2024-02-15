package site.moamoa.backend.repository.notification;

import site.moamoa.backend.domain.Notification;

import java.util.List;

public interface NotificationQueryDSLRepository {

    List<Notification> findNotificationsByMemberId(Long memberId);
}
