package com.back2basics.user.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserType {
    MANAGER("MANAGER"),
    MEMBER("MEMBER");


    private final String key;

    @JsonValue
    public String getKey() {
        return key;
    }
}
