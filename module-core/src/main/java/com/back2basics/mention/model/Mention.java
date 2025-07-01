package com.back2basics.mention.model;

import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mention {

    private final Long id;
    private final Long clientId;
    private final Long projectId;
    private final Long postId;
    private final String content;
    private final NotificationType type;
    private final Boolean isRead;
    private final LocalDateTime createdAt;

    public static Mention createMyMention(
        Long id,
        Long clientId,
        Long projectId,
        Long postId,
        String content,
        NotificationType type,
        Boolean isRead,
        LocalDateTime createdAt
    ) {
        return Mention.builder()
            .id(id)
            .clientId(clientId)
            .type(type)
            .content(content)
            .projectId(projectId)
            .postId(postId)
            .isRead(isRead)
            .createdAt(createdAt)
            .build();
    }
}