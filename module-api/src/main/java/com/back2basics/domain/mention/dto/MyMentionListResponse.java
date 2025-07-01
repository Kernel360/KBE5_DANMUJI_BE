package com.back2basics.domain.mention.dto;

import com.back2basics.mention.service.result.MyMentionListResult;
import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyMentionListResponse {

    private final Long notificationId;
    private final Long clientId;
    private final Long referenceId;
    private final String content;
    private final NotificationType type;
    private final Boolean isRead;
    private final LocalDateTime createdAt;

    public static MyMentionListResponse toResponse(MyMentionListResult result) {
        return MyMentionListResponse.builder()
            .notificationId(result.getNotificationId())
            .type(result.getType())
            .content(result.getContent())
            .referenceId(result.getReferenceId())
            .isRead(result.getIsRead())
            .createdAt(result.getCreatedAt())
            .clientId(result.getClientId())
            .build();
    }
}

