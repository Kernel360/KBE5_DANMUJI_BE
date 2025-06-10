package com.back2basics.notify.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    COMMENT("댓글"),
    POST("게시글"),
    QUESTION("질문"),
    ANSWER("답변");

    private final String description;

    @JsonValue
    public String getDescription() {
        return description;
    }
}
