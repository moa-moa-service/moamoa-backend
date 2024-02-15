package site.moamoa.backend.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
