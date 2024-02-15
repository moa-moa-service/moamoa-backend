package site.moamoa.backend.service.component.query.notification;

import site.moamoa.backend.web.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationQueryService {

    List<NotificationResponseDTO.GetNotification> findNotificationByMemberId(Long memberId);

}
