package com.back2basics.user.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserType {
    DEVELOPER("Developer"),
    CLIENT("Client");

    private final String key;

    @JsonValue
    public String getKey() {
        return key;
    }
}
