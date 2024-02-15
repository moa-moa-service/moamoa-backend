package site.moamoa.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import site.moamoa.backend.domain.common.BaseEntity;
import site.moamoa.backend.domain.enums.NotificationStatus;
import site.moamoa.backend.domain.enums.NotificationType;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String message;
    private NotificationType type; //NEW_PARTICIPATION, QUANTITY_FULFILL, NEW_NOTICE, NEW_COMMENT
    private Long referenceId; // postId or noticeId
    private NotificationStatus status; // READ, UNREAD
}
