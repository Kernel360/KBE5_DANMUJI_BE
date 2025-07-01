package com.back2basics.mention.service.result;

import com.back2basics.mention.model.Mention;
import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyMentionListResult {

    private final Long notificationId;
    private final Long clientId;
    private final Long projectId;
    private final Long postId;
    private final String content;
    private final NotificationType type;
    private final Boolean isRead;
    private final LocalDateTime createdAt;

    public static MyMentionListResult toResult(Mention mention) {
        return MyMentionListResult.builder()
            .notificationId(mention.getId())
            .type(mention.getType())
            .content(mention.getContent())
            .projectId(mention.getProjectId())
            .postId(mention.getPostId())
            .isRead(mention.getIsRead())
            .createdAt(mention.getCreatedAt())
            .clientId(mention.getClientId())
            .build();
    }
}