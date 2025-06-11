package com.back2basics.notify.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    COMMENT("COMMENT"),
    POST("POST"),
    QUESTION("QUESTION"),
    ANSWER("ANSWER"),
    APPROVAL("APPROVAL"),
    STEP_CHANGE("STEP_CHANGE");

    private final String description;

    @JsonValue
    public String getDescription() {
        return description;
    }
}
