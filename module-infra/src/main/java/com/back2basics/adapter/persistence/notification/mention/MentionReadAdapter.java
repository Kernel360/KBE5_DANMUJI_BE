package com.back2basics.adapter.persistence.notification.mention;

import static com.back2basics.adapter.persistence.notification.QNotificationEntity.notificationEntity;

import com.back2basics.mention.model.Mention;
import com.back2basics.mention.port.out.ReadMentionPort;
import com.back2basics.notify.model.NotificationType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MentionReadAdapter implements ReadMentionPort {

    private final JPAQueryFactory queryFactory;
    private final MentionMapper mapper;

    @Override
    public List<Mention> getMentionsByUserId(Long userId) {
        List<MyMentionProjection> projections = queryFactory
            .select(Projections.constructor(
                MyMentionProjection.class,
                notificationEntity.id,
                notificationEntity.clientId,
                notificationEntity.projectId,
                notificationEntity.postId,
                notificationEntity.message.as("content"),
                notificationEntity.type,
                notificationEntity.isRead,
                notificationEntity.createdAt
            ))
            .from(notificationEntity)
            .where(
                notificationEntity.clientId.eq(userId),
                notificationEntity.isRead.isFalse(),
                notificationEntity.type.in(
                    NotificationType.MENTIONED,
                    NotificationType.PROJECT_POST_CREATED, NotificationType.COMMENT_POST_CREATED,
                    NotificationType.COMMENT_REPLY_CREATED, NotificationType.POST_REPLY_CREATED,
                    NotificationType.POST_RESTORED
                ),
                notificationEntity.deletedAt.isNull()
            )
            .orderBy(notificationEntity.createdAt.desc())
            .fetch();

        return projections.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}