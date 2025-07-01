package com.back2basics.mention.model;

import com.back2basics.notify.model.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mention {

    private final Long id;                // 알림 ID
    private final Long clientId;          // 멘션된 사용자 ID
    private final Long referenceId;       // 게시글 or 댓글 ID
    private final String content;         // 멘션 내용
    private final NotificationType type;  // MENTION_POST / COMMENT / REPLY
    private final Boolean isRead;         // 읽음 여부
    private final LocalDateTime createdAt;

    public static Mention createMyMention(
        Long id,
        Long clientId,
        Long referenceId,
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
            .referenceId(referenceId)
            .isRead(isRead)
            .createdAt(createdAt)
            .build();
    }
}