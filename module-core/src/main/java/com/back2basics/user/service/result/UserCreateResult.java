package com.back2basics.user.service.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateResult {

    private final Long id;
    private final String username;
    private final String password;
}
