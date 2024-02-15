package site.moamoa.backend.service.component.query.notification;

import site.moamoa.backend.web.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationQueryService {

    NotificationResponseDTO.GetNotifications findNotificationByMemberId(Long memberId);

}
