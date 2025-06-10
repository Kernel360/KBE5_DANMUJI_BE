package com.back2basics.user.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    DEV("ROLE_DEV"),
    CLIENT("ROLE_CLIENT"),
    ADMIN("ROLE_ADMIN");

    private final String key;

    @JsonValue
    public String getKey() {
        return key;
    }
}
