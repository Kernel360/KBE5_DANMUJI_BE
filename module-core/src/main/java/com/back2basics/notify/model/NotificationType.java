package com.back2basics.notify.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    POST("게시글"),
    COMMENT("댓글"),
    QUESTION("질문"),
    ANSWER("답변"),

    POST_APPROVAL_REQUEST("게시글승인요청"),
    POST_APPROVAL_ACCEPTED("게시글승인완료"),
    POST_APPROVAL_REJECTED("게시글승인거절"),

    COMMENT_POST_CREATED("내 게시글에 댓글이 달렸습니다."),
    COMMENT_REPLY_CREATED("내 댓글에 답글이 달렸습니다."),

    STEP_APPROVAL_REQUEST("단계승인요청"),
    STEP_APPROVAL_ACCEPTED("단계승인완료"),
    STEP_APPROVAL_REJECTED("단계승인거절");

    private final String description;

    @JsonValue
    public String getDescription() {
        return description;
    }
}
