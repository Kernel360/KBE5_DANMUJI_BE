package com.back2basics.adapter.persistence.notification.mention;

import com.back2basics.mention.model.Mention;
import org.springframework.stereotype.Component;

@Component
public class MentionMapper {

    public Mention toDomain(MyMentionProjection projection) {
        return Mention.createMyMention(
            projection.id(),
            projection.clientId(),
            projection.referenceId(),
            projection.content(),
            projection.type(),
            projection.isRead(),
            projection.createdAt()
        );
    }
}
