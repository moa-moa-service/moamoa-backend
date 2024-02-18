package site.moamoa.backend.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQueryDSLRepository {
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.member.id IN :ids")
    void deleteNotificationsByMemberIds(@Param("ids") List<Long> ids);
}
