package site.moamoa.backend.service.module.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.repository.notification.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationModuleServiceImpl implements NotificationModuleService{
    private final NotificationRepository notificationRepository;
    @Override
    public void saveAllNotifications(List<Notification> notificationList){
        notificationRepository.saveAll(notificationList);
    }
}
