package com.back2basics.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    DEV_USER("ROLE_DEV"),
    CLIENT_USER("ROLE_CLIENT"),
    // todo: 고려 사항
//    DEV_ADMIN("ROLE_DEV_ADMIN"),
//    CLIENT_ADMIN("ROLE_CLIENT_ADMIN"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}
