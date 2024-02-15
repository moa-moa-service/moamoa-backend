package site.moamoa.backend.repository.notification;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.domain.QNotification;

import java.util.List;

@RequiredArgsConstructor
public class NotificationQueryDSLRepositoryImpl implements NotificationQueryDSLRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notification> findNotificationsByMemberId(Long memberId) {
        QNotification notification = QNotification.notification;

        BooleanBuilder conditions = new BooleanBuilder();
        addCondition(conditions, notification.member.id.eq(memberId));
        return jpaQueryFactory
                .selectFrom(notification)
                .where(conditions)
                .fetch();
    }
    private void addCondition(BooleanBuilder builder, BooleanExpression condition) {
        builder.and(condition);
    }
}